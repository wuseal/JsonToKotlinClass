package wu.seal.jsontokotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import wu.seal.jsontokotlin.feedback.getUncaughtExceptionHandler
import wu.seal.jsontokotlin.filetype.KotlinFileType
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.ClassCodeFilter
import wu.seal.jsontokotlin.utils.ImportClassDeclaration
import wu.seal.jsontokotlin.utils.executeCouldRollBackAction

/**
 * Created by Seal.Wu on 2018/4/18.
 */
class GenerateKotlinFileAction : AnAction("GenerateKotlinClassFile") {

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString: String = ""
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
                    val codeMaker = KotlinCodeMaker(className, json)
                    val kotlinFileContent = buildString {
                        append(packageDeclare)
                        append("\n\n")
                        append(ImportClassDeclaration.getImportClassDeclaration())
                        append("\n")
                        append(ClassCodeFilter.removeDuplicateClassCode(codeMaker.makeKotlinData()))
                    }

                    executeCouldRollBackAction(project) {
                        val file = psiFileFactory.createFileFromText("$className.kt", KotlinFileType(), kotlinFileContent)
                        directory.add(file)

                    }

                }
            }
        } catch (e: Exception) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    private fun dealWithException(jsonString: String, e: Throwable) {
        var jsonString1 = jsonString
        val yes = Messages.showYesNoDialog("Some thing execute wrong.\nAgree with publishing your JSON text to help us to solve the problem?", "Excuse me", Messages.getQuestionIcon())
        if (yes != Messages.YES) {
            jsonString1 = "User keep private about JSON text"
        }
        getUncaughtExceptionHandler(jsonString1) {
            Messages.showErrorDialog("I am sorry,JsonToKotlinClass may occur a RuntimeException,\nYou could try again later or recover to the old version,\nOr you could post an issue here:\nhttps://github.com/wuseal/JsonToKotlinClass\nWe will fixed it soon!", "Occur a fatal error")
        }.uncaughtException(Thread.currentThread(), e)
    }
}