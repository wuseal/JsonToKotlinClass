package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import wu.seal.jsontokotlin.feedback.StartAction
import wu.seal.jsontokotlin.feedback.SuccessCompleteAction
import wu.seal.jsontokotlin.feedback.dealWithException
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.model.UnSupportJsonException
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.*
import java.net.URL
import java.util.*
import kotlin.math.max

/**
 * Plugin action
 * Created by Seal.Wu on 2017/8/18.
 */
class InsertKotlinClassAction : AnAction("Kotlin data classes from JSON") {

    private val gson = Gson()

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString = ""
        try {
            actionStart()
            val project = event.getData(PlatformDataKeys.PROJECT)
            val caret = event.getData(PlatformDataKeys.CARET)
            val editor = event.getData(PlatformDataKeys.EDITOR_EVEN_IF_INACTIVE)
            if (couldNotInsertCode(editor)) return
            val document = editor?.document ?: return
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
            val json = if (inputString.startsWith("http")) {
                URL(inputString).readText()
            } else inputString
            if (json.isEmpty()) {
                return
            }
            jsonString = json

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
        ClassImportDeclarationWriter.insertImportClassCode(project, document)

        val codeMaker: KotlinClassCodeMaker
        try {
            //passing current file directory along with className and json
            codeMaker = KotlinClassCodeMaker(KotlinClassMaker(className, jsonString).makeKotlinClass())
        } catch (e: IllegalFormatFlagsException) {
            e.printStackTrace()
            Messages.showErrorDialog(e.message, "UnSupport Json")
            return false
        }

        val generateClassesString = codeMaker.makeKotlinClassCode()

        executeCouldRollBackAction(project) {
            var offset: Int

            if (caret != null) {

                offset = caret.offset
                if (offset == 0) {
                    offset = document.textLength
                }
                val lastPackageKeywordLineEndIndex = try {
                    "^[\\s]*package\\s.+\n$".toRegex(RegexOption.MULTILINE).findAll(document.text).last().range.last
                } catch (e: Exception) {
                    -1
                }
                val lastImportKeywordLineEndIndex = try {
                    "^[\\s]*import\\s.+\n$".toRegex(RegexOption.MULTILINE).findAll(document.text).last().range.last
                } catch (e: Exception) {
                    -1
                }
                if (offset < lastPackageKeywordLineEndIndex) {
                    offset = lastPackageKeywordLineEndIndex + 1
                }
                if (offset < lastImportKeywordLineEndIndex) {
                    offset = lastImportKeywordLineEndIndex + 1
                }

            } else {
                offset = document.textLength
            }
            document.insertString(
                    max(offset, 0),
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
        return if (tempCleanText.trim().endsWith("data")) tempCleanText.trim().removeSuffix("data") else tempCleanText
    }

    fun getCurrentEditFileTemClassName(editorText: String) = editorText.substringAfterLast("class")
            .substringBefore("(").substringBefore("{").trim()

    /**
     * whether we could reuse current class name declared in the edit file for inserting data class code
     * if we could use it,then we would clean the kotlin file as it was new file without any class code .
     */
    fun couldGetAndReuseClassNameInCurrentEditFileForInsertCode(editorText: String): Boolean {
        try {
            val removeDocComment = editorText.replace(Regex("/\\*\\*(.|\n)*\\*/", RegexOption.MULTILINE), "")
            val removeDocCommentAndPackageDeclareText = removeDocComment
                .replace(Regex("^(?:\\s*package |\\s*import ).*$", RegexOption.MULTILINE), "")

            removeDocCommentAndPackageDeclareText.run {
                if (numberOf("class") == 1 &&
                    (substringAfter("class").containsAnyOf(listOf("(", ":", "=")).not()
                            || substringAfter("class").substringAfter("(").replace(
                        Regex("\\s"),
                        ""
                    ).let { it == ")" || it == "){}" })
                ) {
                    return true
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return false
    }
}
