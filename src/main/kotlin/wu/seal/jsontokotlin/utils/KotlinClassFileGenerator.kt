package wu.seal.jsontokotlin.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.filetype.KotlinFileType
import wu.seal.jsontokotlin.model.ConfigManager

class KotlinClassFileGenerator {

    fun generateSingleKotlinClassFile(
            packageDeclare: String,
            kotlinClass: KotlinClass,
            project: Project?,
            psiFileFactory: PsiFileFactory,
            directory: PsiDirectory,
            jsonString: String
    ) {
        val fileNamesWithoutSuffix = currentDirExistsFileNamesWithoutKTSuffix(directory)
        var kotlinClassForGenerateFile = kotlinClass
        while (fileNamesWithoutSuffix.contains(kotlinClassForGenerateFile.name)) {
            kotlinClassForGenerateFile =
                    kotlinClassForGenerateFile.rename(newName = kotlinClassForGenerateFile.name + "X")
        }
        generateKotlinClassFile(
                kotlinClassForGenerateFile.name,
                packageDeclare,
                kotlinClassForGenerateFile.getCode(),
                project,
                psiFileFactory,
                directory,
                jsonString
        )
        val notifyMessage = "Kotlin Data Class file generated successful"
        showNotify(notifyMessage, project)
    }

    fun generateMultipleKotlinClassFiles(
            kotlinClass: KotlinClass,
            packageDeclare: String,
            project: Project?,
            psiFileFactory: PsiFileFactory,
            directory: PsiDirectory,
            generatedFromJSONSchema: Boolean,
            jsonString: String
    ) {
        val fileNamesWithoutSuffix = currentDirExistsFileNamesWithoutKTSuffix(directory)
        val existsKotlinFileNames = IgnoreCaseStringSet().also { it.addAll(fileNamesWithoutSuffix) }
        val splitClasses = kotlinClass.resolveNameConflicts(existsKotlinFileNames).getAllModifiableClassesRecursivelyIncludeSelf().run {
            if (!generatedFromJSONSchema) distinctByPropertiesAndSimilarClassName() else this
        }
        splitClasses.forEach { splitDataClass ->
            generateKotlinClassFile(
                    splitDataClass.name,
                    packageDeclare,
                    splitDataClass.getOnlyCurrentCode(),
                    project,
                    psiFileFactory,
                    directory,
                    jsonString
            )
        }
        val notifyMessage = buildString {
            append("${splitClasses.size} Kotlin Data Class files generated successful")
        }
        showNotify(notifyMessage, project)
    }

    private fun currentDirExistsFileNamesWithoutKTSuffix(directory: PsiDirectory): List<String> {
        val kotlinFileSuffix = ".kt"
        return directory.files.filter { it.name.endsWith(kotlinFileSuffix) }
                .map { it.name.dropLast(kotlinFileSuffix.length) }
    }

    private fun getRenameClassMap(originNames: List<String>, currentNames: List<String>): List<Pair<String, String>> {
        if (originNames.size != currentNames.size) {
            throw IllegalArgumentException("two names list must have the same size!")
        }
        val renameMap = mutableListOf<Pair<String, String>>()
        originNames.forEachIndexed { index, originName ->
            if (originName != currentNames[index]) {
                renameMap.add(Pair(originName, currentNames[index]))
            }
        }
        return renameMap
    }

    private fun generateKotlinClassFile(
            fileName: String,
            packageDeclare: String,
            classCodeContent: String,
            project: Project?,
            psiFileFactory: PsiFileFactory,
            directory: PsiDirectory,
            jsonString: String
    ) {
        val kotlinFileContent = buildString {
            if (packageDeclare.isNotEmpty()) {
                append(packageDeclare)
                append("\n\n")
            }
            val importClassDeclaration = ClassImportDeclaration.getImportClassDeclaration()
            if (importClassDeclaration.isNotBlank()) {
                append(importClassDeclaration)
                append("\n\n")
            }
            if (ConfigManager.isAppendOriginalJson) {
                append(jsonString.toJavaDocMultilineComment())
            }
            append(classCodeContent)
        }
        
        var finalFileName = fileName.trim('`')
        var ktFileName = "$finalFileName.kt"
        
        // Check if file already exists and rename it by adding 'X' suffix if needed
        val fileNamesWithoutSuffix = currentDirExistsFileNamesWithoutKTSuffix(directory)
        while (fileNamesWithoutSuffix.contains(finalFileName)) {
            finalFileName += "X"
            ktFileName = "$finalFileName.kt"
        }
        
        // Create the file with the potentially modified name
        executeCouldRollBackAction(project) {
            val file = psiFileFactory.createFileFromText(ktFileName, KotlinFileType(), kotlinFileContent)
            directory.add(file)
        }
    }
}
