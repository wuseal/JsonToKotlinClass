package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import wu.seal.jsontokotlin.bean.jsonschema.JsonSchema
import wu.seal.jsontokotlin.feedback.dealWithException
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.ClassCodeFilter
import wu.seal.jsontokotlin.utils.KotlinDataClassFileGenerator


/**
 * Created by Seal.Wu on 2018/4/18.
 */
class GenerateKotlinFileAction : AnAction("GenerateKotlinClassFile") {

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString = ""
        try {
            val project = event.getData(PlatformDataKeys.PROJECT) ?: return

            val dataContext = event.dataContext
            val module = LangDataKeys.MODULE.getData(dataContext) ?: return

            val navigatable = LangDataKeys.NAVIGATABLE.getData(dataContext)
            val directory: PsiDirectory =
                if (navigatable is PsiDirectory) {
                    navigatable
                } else if (navigatable is PsiFile) {
                    navigatable.containingDirectory
                } else {
                    val root = ModuleRootManager.getInstance(module)
                    var tempDirectory: PsiDirectory? = null
                    for (file in root.sourceRoots) {
                        tempDirectory = PsiManager.getInstance(project).findDirectory(file)
                        if (tempDirectory != null) {
                            break
                        }
                    }
                    tempDirectory
                } ?: return

            val directoryFactory = PsiDirectoryFactory.getInstance(directory.project)
            val packageName = directoryFactory.getQualifiedName(directory, false)
            val psiFileFactory = PsiFileFactory.getInstance(project)
            val packageDeclare = if (packageName.isNotEmpty()) "package $packageName" else ""
            val inputDialog = JsonInputDialog("", project)
            inputDialog.show()
            val className = inputDialog.getClassName()
            val inputString = inputDialog.inputString
            if (inputString.isEmpty()) {
                return
            }
            jsonString = inputString
            doGenerateKotlinDataClassFileAction(
                className,
                    inputString,
                packageDeclare,
                project,
                psiFileFactory,
                directory
            )
        } catch (e: Throwable) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    private fun doGenerateKotlinDataClassFileAction(
        className: String,
        json: String,
        packageDeclare: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {
        val generatedClassesString = try {
            val jsonSchema = Gson().fromJson<JsonSchema>(json, JsonSchema::class.java)
            if (jsonSchema.schema?.isNotBlank() != true) {
                throw IllegalArgumentException("input string is not valid json schema")
            }
            val generator = JsonSchemaDataClassGenerator(jsonSchema)
            generator.generate(className)
            generator.classes.joinToString("\n") { it.toString() }
        } catch (e: Exception) {
            val codeMaker = KotlinCodeMaker(className, json)
            codeMaker.makeKotlinData()
        }

        val removeDuplicateClassCode = ClassCodeFilter.removeDuplicateClassCode(generatedClassesString)

        if (ConfigManager.isInnerClassModel) {

            KotlinDataClassFileGenerator().generateSingleDataClassFile(
                className,
                packageDeclare,
                removeDuplicateClassCode,
                project,
                psiFileFactory,
                directory
            )

        } else {

            KotlinDataClassFileGenerator().generateMultipleDataClassFiles(
                removeDuplicateClassCode,
                packageDeclare,
                project,
                psiFileFactory,
                directory
            )

        }
    }
}
