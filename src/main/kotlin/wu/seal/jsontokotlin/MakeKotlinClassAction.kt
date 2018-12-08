package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import wu.seal.jsontokotlin.feedback.StartAction
import wu.seal.jsontokotlin.feedback.SuccessCompleteAction
import wu.seal.jsontokotlin.feedback.dealWithException
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.ClassCodeFilter
import wu.seal.jsontokotlin.utils.executeCouldRollBackAction
import java.util.*

/**
 * Plugin action
 * Created by Seal.Wu on 2017/8/18.
 */
class MakeKotlinClassAction : AnAction("MakeKotlinClass") {

    private val gson = Gson()
    lateinit var directory: PsiDirectory //directory object to avoid duplicate/repetitive class names

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString = ""
        try {
            actionStart()

            // initializing the directory object to check duplicate class names
            initDirectory(event)

            val project = event.getData(PlatformDataKeys.PROJECT)
            val caret = event.getData(PlatformDataKeys.CARET)
            val editor = event.getData(PlatformDataKeys.EDITOR_EVEN_IF_INACTIVE)
            if (couldNotInsertCode(editor)) return
            val document = editor!!.document
            val editorText = document.text
            /**
             *  temp class name for insert
             */
            var tempClassName = ""
            val couldGetAndReuseClassNameInCurrentEditFileForInsertCode =
                    couldGetAndReuseClassNameInCurrentEditFileForInsertCode(editorText)

            if (couldGetAndReuseClassNameInCurrentEditFileForInsertCode) {
                /**
                 * auto obtain the current class name
                 */
                tempClassName = getCurrentEditFileTemClassName(editorText)
            }
            val inputDialog = JsonInputDialog(tempClassName, project!!)
            inputDialog.show()
            val className = inputDialog.getClassName()
            val inputString = inputDialog.inputString
            if (inputString.isEmpty()) {
                return
            }
            jsonString = inputString

            if (reuseClassName(couldGetAndReuseClassNameInCurrentEditFileForInsertCode, className, tempClassName)) {
                executeCouldRollBackAction(project) {
                    /**
                     * if you don't clean then we will trick a conflict with two same class name error
                     */
                    cleanCurrentEditFile(document)
                }
            }
            if (insertKotlinCode(project, document, className, jsonString, caret)) {
                actionComplete()
            }


        } catch (e: UnSupportJsonException) {
            val advice = e.advice
            Messages.showInfoMessage(dealWithHtmlConvert(advice), "Tip")
        } catch (e: Throwable) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    /**
     * initializes the value of directory of the current file
     */
    private fun initDirectory(event: AnActionEvent)
    {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return

        val dataContext = event.dataContext
        val module = LangDataKeys.MODULE.getData(dataContext) ?: return

        val navigatable = LangDataKeys.PSI_FILE.getData(dataContext)

        directory =
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
    }

    private fun dealWithHtmlConvert(advice: String) = advice.replace("<", "&lt;").replace(">", "&gt;")

    private fun reuseClassName(
            couldGetAndReuseClassNameInCurrentEditFileForInserCode: Boolean,
            className: String,
            tempClassName: String
    ) = couldGetAndReuseClassNameInCurrentEditFileForInserCode && className == tempClassName

    private fun couldNotInsertCode(editor: Editor?): Boolean {
        if (editor == null || editor.document.isWritable.not()) {
            Messages.showWarningDialog("Please open a file in edited state for inserting Kotlin code!", "No Edited File")
            return true
        }
        return false
    }

    private fun actionComplete() {
        Thread {
            sendActionInfo(gson.toJson(SuccessCompleteAction()))
        }.start()
    }

    private fun actionStart() {
        Thread {
            sendActionInfo(gson.toJson(StartAction()))
        }.start()
    }

    private fun insertKotlinCode(
            project: Project?,
            document: Document,
            className: String,
            jsonString: String,
            caret: Caret?
    ): Boolean {
        ImportClassWriter.insertImportClassCode(project, document)

        val codeMaker: KotlinDataClassCodeMaker
        try {
            //passing current file directory along with className and json
            codeMaker = KotlinDataClassCodeMaker(className, jsonString, directory)
        } catch (e: IllegalFormatFlagsException) {
            e.printStackTrace()
            Messages.showErrorDialog(e.message, "UnSupport Json")
            return false
        }

        val generateClassesString = codeMaker.makeKotlinDataClassCode()

        executeCouldRollBackAction(project) {
            var offset: Int

            if (caret != null) {

                offset = caret.offset
                if (offset == 0) {
                    offset = document.textLength
                }
            } else {
                offset = document.textLength
            }
            document.insertString(
                    Math.max(offset, 0),
                    ClassCodeFilter.removeDuplicateClassCode(generateClassesString)
            )
        }
        return true
    }

    private fun cleanCurrentEditFile(document: Document, editorText: String = document.text) {
        val cleanText = getCleanText(editorText)
        document.setText(cleanText)
    }

    fun getCleanText(editorText: String): String {
        val tempCleanText = editorText.substringBeforeLast("class")
        val cleanText =
                if (tempCleanText.trim().endsWith("data")) tempCleanText.trim().removeSuffix("data") else tempCleanText
        return cleanText
    }

    fun getCurrentEditFileTemClassName(editorText: String) = editorText.substringAfterLast("class")
            .substringBefore("(").substringBefore("{").trim()

    /**
     * whether we could reuse current class name declared in the edit file for inserting data class code
     * if we could use it,then we would clean the kotlin file as it was new file without any class code .
     */
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCode(editorText: String): Boolean {
        try {
            var couldGetAndReuseClassNameInCurrentEditFileForInsertCode = false
            val removeDocComment = editorText.replace(Regex("/\\*\\*(.|\n)*\\*/", RegexOption.MULTILINE), "")
            val removeDocCommentAndPackageDeclareText = removeDocComment
                    .replace(Regex("^(?:\\s*package |\\s*import ).*$", RegexOption.MULTILINE), "")
            if ((removeDocCommentAndPackageDeclareText.indexOf("class") == removeDocCommentAndPackageDeclareText.lastIndexOf(
                            "class"
                    )
                            && removeDocCommentAndPackageDeclareText.indexOf("class") != -1
                            && removeDocCommentAndPackageDeclareText.substringAfter("class").contains("(").not()
                            && removeDocCommentAndPackageDeclareText.substringAfter("class").contains(":").not()
                            && removeDocCommentAndPackageDeclareText.substringAfter("class").contains("=").not())
                    || (removeDocCommentAndPackageDeclareText.indexOf("class") == removeDocCommentAndPackageDeclareText.lastIndexOf(
                            "class"
                    )
                            && removeDocCommentAndPackageDeclareText.indexOf("class") != -1
                            && removeDocCommentAndPackageDeclareText.substringAfter("class").substringAfter("(")
                            .replace(Regex("\\s"), "").let { it.equals(")") || it.equals("){}") })
            ) {
                couldGetAndReuseClassNameInCurrentEditFileForInsertCode = true
            }
            return couldGetAndReuseClassNameInCurrentEditFileForInsertCode
        } catch (e: Throwable) {
            return false
        }
    }
}
