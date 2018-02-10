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
import wu.seal.jsontokotlin.feedback.getUncaughtExceptionHandler
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.ui.JsonInputDialog
import wu.seal.jsontokotlin.utils.executeCouldRollBackAction

import java.util.IllegalFormatFlagsException

/**
 * Plugin action
 * Created by Seal.Wu on 2017/8/18.
 */
class MakeKotlinClassAction : AnAction("MakeKotlinClass") {

    private val gson = Gson()

    override fun actionPerformed(event: AnActionEvent) {
        var jsonString: String = ""
        try {
            actionStart()
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
            val couldGetAndReuseClassNameInCurrentEditFileForInsertCode = couldGetAndReuseClassNameInCurrentEditFileForInsertCode(editorText)

            if (couldGetAndReuseClassNameInCurrentEditFileForInsertCode) {
                /**
                 * auto obtain the current class name
                 */
                tempClassName = getCurrentEditFileTemClassName(editorText)
            }
            val inputDialog = JsonInputDialog(tempClassName, project!!)
            inputDialog.show()
            val className = inputDialog.getClassName()
            val json = inputDialog.inputString
            if (json == null || json.isEmpty()) {
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


        } catch(e: Exception) {
            dealWithException(jsonString, e)
            throw e
        }
    }

    private fun reuseClassName(couldGetAndReuseClassNameInCurrentEditFileForInserCode: Boolean, className: String, tempClassName: String) = couldGetAndReuseClassNameInCurrentEditFileForInserCode && className == tempClassName

    private fun couldNotInsertCode(editor: Editor?): Boolean {
        if (editor == null) {
            Messages.showWarningDialog("Please open a file in editor state for insert Kotlin code!", "No Editor File")
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

    private fun dealWithException(jsonString: String, e: Exception) {
        var jsonString1 = jsonString
        val yes = Messages.showYesNoDialog("Some thing execute wrong.\nAgree with publishing your JSON text to help us to solve the problem?", "Excuse me", Messages.getQuestionIcon())
        if (yes != Messages.YES) {
            jsonString1 = "User keep private about JSON text"
        }
        getUncaughtExceptionHandler(jsonString1) {
            Messages.showErrorDialog("I am sorry,JsonToKotlinClass may occur a RuntimeException,\nYou could try again later or recover to the old version,\nOr you could post an issue here:\nhttps://github.com/wuseal/JsonToKotlinClass\nWe will fixed it soon!", "Occur a fatal error")
        }.uncaughtException(Thread.currentThread(), e)
    }

    private fun insertKotlinCode(project: Project?, document: Document, className: String, jsonString: String, caret: Caret?): Boolean {
        ImportClassWriter.insertImportClassCode(project, document)

        val codeMaker: KotlinCodeMaker
        try {
            codeMaker = KotlinCodeMaker(className, jsonString)
        } catch (e: IllegalFormatFlagsException) {
            e.printStackTrace()
            Messages.showErrorDialog(e.message, "UnSupport Json")
            return false
        }

        executeCouldRollBackAction(project) {
            var offset: Int

            if (caret != null) {

                offset = caret.offset
                if (offset == 0) {
                    offset = document.textLength - 1
                }
            } else {
                offset = document.textLength - 1
            }
            document.insertString(Math.max(offset, 0), codeMaker.makeKotlinData())
        }
        return true
    }

    private fun cleanCurrentEditFile(document: Document, editorText: String = document.text) {
        document.setText(editorText.substringBefore("class"))
    }

    private fun getCurrentEditFileTemClassName(editorText: String) = editorText.substringAfter("class").substringBefore("{").trim()

    /**
     * whether we could reuse current class name declared in the edit file for inserting data class code
     * if we could use it,then we would clean the kotlin file as it was new file without any class code .
     */
    private fun couldGetAndReuseClassNameInCurrentEditFileForInsertCode(editorText: String): Boolean {
        var couldGetAndReuseClassNameInCurrentEditFileForInsertCode = false
        if (editorText.indexOf("class") == editorText.lastIndexOf("class")
                && editorText.substringAfter("class").contains("(").not()
                && editorText.substringAfter("class").contains(":").not()
                && editorText.substringAfter("class").contains("=").not()) {
            couldGetAndReuseClassNameInCurrentEditFileForInsertCode = true
        }
        return couldGetAndReuseClassNameInCurrentEditFileForInsertCode
    }
}
