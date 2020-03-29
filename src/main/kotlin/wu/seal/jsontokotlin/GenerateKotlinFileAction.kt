package wu.seal.jsontokotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import wu.seal.jsontokotlin.feedback.dealWithException
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.UnSupportJsonException
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.KotlinClassFileGenerator
import wu.seal.jsontokotlin.utils.KotlinClassMaker


/**
 * Created by Seal.Wu on 2018/4/18.
 */
class GenerateKotlinFileAction : AnAction("Kotlin data class File from JSON") {

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString = ""
        try {
            val project = event.getData(PlatformDataKeys.PROJECT) ?: return

            val dataContext = event.dataContext
            val module = LangDataKeys.MODULE.getData(dataContext) ?: return

            val directory = when (val navigatable = LangDataKeys.NAVIGATABLE.getData(dataContext)) {
                is PsiDirectory -> navigatable
                is PsiFile -> navigatable.containingDirectory
                else -> {
                    val root = ModuleRootManager.getInstance(module)
                    root.sourceRoots
                        .asSequence()
                        .mapNotNull {
                            PsiManager.getInstance(project).findDirectory(it)
                        }.firstOrNull()
                }
            } ?: return

            val directoryFactory = PsiDirectoryFactory.getInstance(directory.project)
            val packageName = directoryFactory.getQualifiedName(directory, false)
            val psiFileFactory = PsiFileFactory.getInstance(project)
            val packageDeclare = if (packageName.isNotEmpty()) "package $packageName" else ""
            val inputDialog = JsonInputDialog("", project)
            inputDialog.show()
            val className = inputDialog.getClassName()
            val inputString = inputDialog.inputString.takeIf { it.isNotEmpty() } ?: return

            jsonString = inputString
            doGenerateKotlinDataClassFileAction(
                className,
                inputString,
                packageDeclare,
                project,
                psiFileFactory,
                directory
            )
        } catch (e: UnSupportJsonException) {
            val advice = e.advice
            Messages.showInfoMessage(dealWithHtmlConvert(advice), "Tip")
        } catch (e: Throwable) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    private fun dealWithHtmlConvert(advice: String) = advice.replace("<", "&lt;").replace(">", "&gt;")

    private fun doGenerateKotlinDataClassFileAction(
        className: String,
        json: String,
        packageDeclare: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {
        val kotlinClass = KotlinClassMaker(className, json).makeKotlinClass()
        val dataClassAfterApplyInterceptor =
            kotlinClass.applyInterceptors(InterceptorManager.getEnabledKotlinDataClassInterceptors())
        if (ConfigManager.isInnerClassModel) {

            KotlinClassFileGenerator().generateSingleKotlinClassFile(
                packageDeclare,
                dataClassAfterApplyInterceptor,
                project,
                psiFileFactory,
                directory
            )
        } else {
            KotlinClassFileGenerator().generateMultipleKotlinClassFiles(
                dataClassAfterApplyInterceptor,
                packageDeclare,
                project,
                psiFileFactory,
                directory
            )
        }
    }
}
