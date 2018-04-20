package wu.seal.jsontokotlin

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import wu.seal.jsontokotlin.feedback.dealWithException
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
                    val removeDuplicateClassCode = ClassCodeFilter.removeDuplicateClassCode(codeMaker.makeKotlinData())

                    if (ConfigManager.isInnerClassModel) {

                        generateKotlinDataClassFile(packageDeclare, removeDuplicateClassCode, project, psiFileFactory, className, directory)
                        val notifyMessage = "Kotlin Data Class file generated successful"
                        showNotify(notifyMessage, project)

                    } else {
                        var classCount = 0
                        removeDuplicateClassCode.split("\n\n").forEach {
                            if (it.contains("data class")) {
                                classCount++
                                val aClassName = it.substringAfter("data class").substringBefore("(").trim()
                                generateKotlinDataClassFile(packageDeclare, it, project, psiFileFactory, aClassName, directory)
                            }
                        }
                        val notifyMessage = "$classCount Kotlin Data Class file generated successfully"
                        showNotify(notifyMessage, project)

                    }


                }
            }
        } catch (e: Exception) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    private fun generateKotlinDataClassFile(packageDeclare: String, classCodeContent: String, project: Project?, psiFileFactory: PsiFileFactory, className: String, directory: PsiDirectory) {
        val kotlinFileContent = buildString {
            append(packageDeclare)
            append("\n\n")
            append(ImportClassDeclaration.getImportClassDeclaration())
            append("\n")
            append(classCodeContent)
        }

        executeCouldRollBackAction(project) {
            val file = psiFileFactory.createFileFromText("$className.kt", KotlinFileType(), kotlinFileContent)
            directory.add(file)
        }
    }

    private fun showNotify(notifyMessage: String, project: Project?) {
        val notificationGroup = NotificationGroup("JSON to Kotlin Class", NotificationDisplayType.BALLOON, true)
        ApplicationManager.getApplication().invokeLater {
            val notification = notificationGroup.createNotification(notifyMessage, NotificationType.INFORMATION)
            Notifications.Bus.notify(notification, project)
        }
    }
}