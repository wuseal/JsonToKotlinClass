package wu.seal.jsontokotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
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
            val project = event.getData(PlatformDataKeys.PROJECT)
            project?.let {
                val dataContext = event.dataContext
                val module = DataKeys.MODULE.getData(dataContext)
                module?.let {
                    val navigatable = DataKeys.NAVIGATABLE.getData(dataContext)
                    val directory: PsiDirectory =
                        if (navigatable is PsiDirectory) {
                            navigatable
                        } else {
                            val root = ModuleRootManager.getInstance(module)
                            var tempDirectory: PsiDirectory? = null
                            for (file in root.sourceRoots) {
                                tempDirectory = PsiManager.getInstance(project).findDirectory(file)
                            }
                            tempDirectory!!
                        }
                    val directoryFactory = PsiDirectoryFactory.getInstance(directory.getProject())
                    val packageName = directoryFactory.getQualifiedName(directory, true)
                    val psiFileFactory = PsiFileFactory.getInstance(project)
                    val packageDeclare = "package $packageName"
                    val inputDialog = JsonInputDialog("", project)
                    inputDialog.show()
                    val className = inputDialog.getClassName()
                    val json = inputDialog.inputString
                    if (json == null || json.isEmpty()) {
                        return
                    }
                    jsonString = json
                    doGenerateKotlinDataClassFileAction(
                        className,
                        json,
                        packageDeclare,
                        project,
                        psiFileFactory,
                        directory
                    )

                }
            }
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
        val codeMaker = KotlinCodeMaker(className, json)
        val removeDuplicateClassCode = ClassCodeFilter.removeDuplicateClassCode(codeMaker.makeKotlinData())

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