package wu.seal.jsontokotlin.utils

import com.intellij.codeInsight.actions.AbstractLayoutCodeProcessor
import com.intellij.codeInsight.actions.OptimizeImportsProcessor
import com.intellij.codeInsight.actions.RearrangeCodeProcessor
import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.filetype.KotlinFileType
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NestedClassModelClassesCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NormalClassesCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass

class KotlinDataClassFileGenerator(private val interceptors: List<IKotlinDataClassInterceptor> = InterceptorManager.getEnabledKotlinDataClassInterceptors()) {

    /**
     * record the renamed class name when generate multiple files
     */
    private val renamedClassNames = mutableListOf<Pair<String, String>>()

    fun generateSingleDataClassFile(
        className: String,
        packageDeclare: String,
        removeDuplicateClassCode: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {
        var fileName = className

        fileName = changeKotlinFileNameIfCurrentDirectoryExistTheSameFileNameWithoutSuffix(fileName, directory)

        generateKotlinDataClassFile(
            fileName,
            packageDeclare,
            removeDuplicateClassCode,
            project,
            psiFileFactory,
            directory
        )
        val notifyMessage = "Kotlin Data Class file generated successful"
        showNotify(notifyMessage, project)
    }




    fun generateMultipleDataClassFiles(
        removeDuplicateClassCode: String,
        packageDeclare: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {

        val tobeGenerateFilesClasses = getToBeGenerateClassess(removeDuplicateClassCode, directory)

        tobeGenerateFilesClasses.forEach { kotlinDataClass ->
            generateKotlinDataClassFile(
                kotlinDataClass.name,
                packageDeclare,
                kotlinDataClass.toString(),
                project,
                psiFileFactory,
                directory
            )
        }
        val notifyMessage = buildString {
            append("${tobeGenerateFilesClasses.size} Kotlin Data Class files generated successful")
            if (renamedClassNames.isNotEmpty()) {
                append("\n")
                append("These class names has been auto renamed to new names:\n ${renamedClassNames.map { it.first + " -> " + it.second }.toList()}")
            }
        }
        showNotify(notifyMessage, project)

    }

    fun getToBeGenerateClassess(removeDuplicateClassCode: String, directory: PsiDirectory) : List<ParsedKotlinDataClass>
    {
        val classes =
                getClassesStringList(removeDuplicateClassCode).map { ClassCodeParser(it).getKotlinDataClass() }

        /**
         * Build Property Type reference to ParsedKotlinDataClass
         * Only pre class property type could reference behind classes
         */
        val buildRefClasses = buildTypeReference(classes)

        val newClassNames = getNoneConflictClassNames(buildRefClasses, directory)

        val newKotlinClasses = updateClassNames(buildRefClasses, newClassNames)


        return synchronizedPropertyTypeWithTypeRef(newKotlinClasses)

    }

    fun updateClassNames(
        dataClasses: List<ParsedKotlinDataClass>,
        newClassNames: List<String>
    ): List<ParsedKotlinDataClass> {

        val newKotlinClasses = dataClasses.toMutableList()

        newKotlinClasses.forEachIndexed { index, kotlinDataClass ->

            val newClassName = newClassNames[index]
            val originClassName = kotlinDataClass.name

            if (newClassName != originClassName) {
                renamedClassNames.add(Pair(originClassName, newClassName))
                val newKotlinDataClass = kotlinDataClass.copy(name = newClassName)
                newKotlinClasses[index] = newKotlinDataClass
                updateTypeRef(dataClasses, kotlinDataClass, newKotlinDataClass)
            }
        }

        return newKotlinClasses
    }

    /**
     * None conflict with current directory files and exist class
     */
    private fun getNoneConflictClassNames(
        buildRefClasses: List<ParsedKotlinDataClass>,
        directory: PsiDirectory
    ): List<String> {
        val resolveSameConflictClassesNames = mutableListOf<String>()
        buildRefClasses.forEach {
            val originClassName = it.name
            var newClassName =
                changeKotlinFileNameIfCurrentDirectoryExistTheSameFileNameWithoutSuffix(originClassName, directory)
            newClassName = changeClassNameIfCurrentListContains(resolveSameConflictClassesNames, newClassName)
            resolveSameConflictClassesNames.add(newClassName)
        }

        return resolveSameConflictClassesNames
    }

    fun updateTypeRef(
        classes: List<ParsedKotlinDataClass>,
        originDataClass: ParsedKotlinDataClass,
        newKotlinDataClass: ParsedKotlinDataClass
    ) {
        classes.forEach {
            it.properties.forEach {
                if (it.kotlinDataClassPropertyTypeRef == originDataClass) {
                    it.kotlinDataClassPropertyTypeRef = newKotlinDataClass
                }
            }
        }
    }

    fun synchronizedPropertyTypeWithTypeRef(unSynchronizedTypeClasses: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {
        val synchronizedPropertyTypeClassList = unSynchronizedTypeClasses.map {

            val dataClass = it
            val newProperties = dataClass.properties.map {
                if (it.kotlinDataClassPropertyTypeRef != ParsedKotlinDataClass.NONE) {
                    val rawPropertyReferenceType = getRawType(getChildType(it.propertyType))
                    val tobeReplaceNewType =
                        it.propertyType.replace(rawPropertyReferenceType, it.kotlinDataClassPropertyTypeRef.name)
                    if (it.propertyValue.isNotBlank()) {
                        it.copy(propertyType = tobeReplaceNewType, propertyValue = getDefaultValue(tobeReplaceNewType))
                    } else
                        it.copy(propertyType = tobeReplaceNewType)
                } else {
                    it
                }
            }
            dataClass.copy(properties = newProperties)
        }
        return synchronizedPropertyTypeClassList
    }

    fun buildTypeReference(classes: List<ParsedKotlinDataClass>): List<ParsedKotlinDataClass> {
        val classNameList = classes.map { it.name }

        /**
         * Build Property Type reference to ParsedKotlinDataClass
         * Only pre class property type could reference behind classes
         */
        classes.forEachIndexed { index, kotlinDataClass ->
            kotlinDataClass.properties.forEachIndexed { _, property ->
                val indexOfClassName =
                    classNameList.firstIndexAfterSpecificIndex(getRawType(getChildType(property.propertyType)), index)
                if (indexOfClassName != -1) {
                    property.kotlinDataClassPropertyTypeRef = classes[indexOfClassName]
                }
            }
        }

        return classes
    }

    private fun generateKotlinDataClassFile(
        fileName: String,
        packageDeclare: String,
        classCodeContent: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {
        val classCode = if (interceptors.isNotEmpty()) {
            if (ConfigManager.isInnerClassModel) {
                NestedClassModelClassesCodeParser(classCodeContent).parse().applyInterceptors(interceptors).getCode()
            } else {
                NormalClassesCodeParser(classCodeContent).parse()[0].applyInterceptors(interceptors).getCode()
            }
        } else {
            classCodeContent
        }
        val kotlinFileContent = buildString {
            if (packageDeclare.isNotEmpty()) {
                append(packageDeclare)
                append("\n\n")
            }
            val importClassDeclaration = ImportClassDeclaration.getImportClassDeclaration()
            if (importClassDeclaration.isNotBlank()) {
                append(importClassDeclaration)
                append("\n\n")
            }
            append(classCode)
        }

        executeCouldRollBackAction(project) {
            val file = psiFileFactory.createFileFromText("$fileName.kt", KotlinFileType(), kotlinFileContent)

            val fileAdded = directory.add(file)

            if (ConfigManager.enableAutoReformat) {
                var processor: AbstractLayoutCodeProcessor =
                    ReformatCodeProcessor(project, fileAdded as PsiFile, null, false)
                processor = OptimizeImportsProcessor(processor)
                processor = RearrangeCodeProcessor(processor)
                processor.run()
            }
        }
    }

    private fun changeKotlinFileNameIfCurrentDirectoryExistTheSameFileNameWithoutSuffix(
        fileName: String,
        directory: PsiDirectory
    ): String {
        var newFileName = fileName
        val kotlinFileSuffix = ".kt"
        val fileNamesWithoutSuffix =
            directory.files.filter { it.name.endsWith(kotlinFileSuffix) }
                .map { it.name.dropLast(kotlinFileSuffix.length) }
        while (fileNamesWithoutSuffix.contains(newFileName)) {
            newFileName += "X"
        }
        return newFileName
    }

    private fun changeClassNameIfCurrentListContains(classesNames: List<String>, className: String): String {
        var newClassName = className
        while (classesNames.contains(newClassName)) {
            newClassName += "X"
        }
        return newClassName
    }
}