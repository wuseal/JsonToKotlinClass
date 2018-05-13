package wu.seal.jsontokotlin.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import wu.seal.jsontokotlin.filetype.KotlinFileType
import wu.seal.jsontokotlin.utils.classblockparse.ClassBlockStringParser
import wu.seal.jsontokotlin.utils.classblockparse.KotlinDataClass

class KotlinDataClassFileGenerator {

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

        val classes =
            getClassesStringList(removeDuplicateClassCode).map { ClassBlockStringParser(it).getKotlinDataClass() }

        /**
         * Build Property Type reference to KotlinDataClass
         * Only pre class property type could reference behind classes
         */
        val buildRefClasses = buildTypeReference(classes)

        val newClassNames = getNoneConflictClassNames(buildRefClasses, directory)

        val newKotlinClasses = updateClassNames(buildRefClasses, newClassNames)

        val tobeGenerateFilesClasses =
            synchronizedPropertyTypeWithTypeRef(newKotlinClasses)

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
            append("\n")
            append("These class names has been auto renamed to new names:\n ${renamedClassNames.map { it.first + " -> " + it.second }.toList()}")
        }
        showNotify(notifyMessage, project)

    }

    internal fun updateClassNames(dataClasses: List<KotlinDataClass>, newClassNames: List<String>):List<KotlinDataClass> {

        val newKotlinClasses = dataClasses.toMutableList()

        newKotlinClasses.forEachIndexed { index, kotlinDataClass ->

            val newClassName = newClassNames[index]
            val originClassName = kotlinDataClass.name

            if (newClassName != originClassName) {
                renamedClassNames.add(Pair(originClassName,newClassName))
                val newKotlinDataClass = kotlinDataClass.copy(name = newClassName)
                newKotlinClasses[index]=newKotlinDataClass
                updateTypeRef(dataClasses, kotlinDataClass, newKotlinDataClass)
            }
        }

        return newKotlinClasses
    }

    /**
     * None conflict with current directory files and exist class
     */
    private fun getNoneConflictClassNames(
        buildRefClasses: List<KotlinDataClass>,
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
        classes: List<KotlinDataClass>,
        originDataClass: KotlinDataClass,
        newKotlinDataClass: KotlinDataClass
    ) {
        classes.forEach {
            it.properties.forEach {
                if (it.kotlinDataClassPropertyTypeRef == originDataClass) {
                    it.kotlinDataClassPropertyTypeRef = newKotlinDataClass
                }
            }
        }
    }

    internal fun synchronizedPropertyTypeWithTypeRef(unSynchronizedTypeClasses: List<KotlinDataClass>): List<KotlinDataClass> {
        val synchronizedPropertyTypeClassList = unSynchronizedTypeClasses.map {

            val dataClass = it
            val newProperties = dataClass.properties.map {
                if (it.kotlinDataClassPropertyTypeRef != KotlinDataClass.NONE) {
                    val rawPropertyReferenceType = getRawType(getChildType(it.propertyType))
                    val tobeReplaceNewType =
                        it.propertyType.replace(rawPropertyReferenceType, it.kotlinDataClassPropertyTypeRef.name)
                    if (it.propertyValue.isNotBlank()) {
                        it.copy(propertyType = tobeReplaceNewType, propertyValue = "$tobeReplaceNewType()")
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

    internal fun buildTypeReference(classes: List<KotlinDataClass>): List<KotlinDataClass> {
        val classNameList = classes.map { it.name }

        /**
         * Build Property Type reference to KotlinDataClass
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
        val kotlinFileContent = buildString {
            append(packageDeclare)
            append("\n\n")
            append(ImportClassDeclaration.getImportClassDeclaration())
            append("\n")
            append(classCodeContent)
        }

        executeCouldRollBackAction(project) {
            val file = psiFileFactory.createFileFromText("$fileName.kt", KotlinFileType(), kotlinFileContent)
            directory.add(file)
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

    private fun changeClassNameIfCurrentMapContains(
        classNameBlockMap: MutableMap<String, KotlinDataClass>,
        fileName: String
    ): String {
        var newFileName = fileName
        while (classNameBlockMap.containsKey(newFileName)) {
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