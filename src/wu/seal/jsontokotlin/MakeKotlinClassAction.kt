package wu.seal.jsontokotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

import java.util.IllegalFormatFlagsException

/**
 * Plugin action
 * Created by Seal.Wu on 2017/8/18.
 */
class MakeKotlinClassAction : AnAction("MakeKotlinClass") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT)
        val caret = event.getData(PlatformDataKeys.CARET)
        val editor = event.getData(PlatformDataKeys.EDITOR_EVEN_IF_INACTIVE)
        if (editor == null) {
            Messages.showWarningDialog("Please open a file in editor state for insert Kotlin code!", "No Editor File")
            return
        }
        val className = Messages.showInputDialog(project, "Please input the Class Name for Insert", "Input ClassName", Messages.getInformationIcon())
        if (className == null || className.isEmpty()) {
            return
        }
        val inputDialog = JsonInputDialog(project!!)
        inputDialog.show()
        val jsonString = inputDialog.inputString
        if (jsonString == null || jsonString.isEmpty()) {
            return
        }
        val document = editor.document
        ImportClassWriter.insertImportClassCode(project, document)

        val maker: KotlinMaker
        try {
            maker = KotlinMaker(className, jsonString)
        } catch (e: IllegalFormatFlagsException) {
            e.printStackTrace()
            Messages.showErrorDialog(e.message, "UnSupport Json")
            return
        }

        executeCouldRollBackAction(project){
            var offset = 0

            if (caret != null) {

                offset = caret.offset
                if (offset == 0) {
                    offset = document.textLength - 1
                }
            } else {
                offset = document.textLength - 1
            }
            document.insertString(Math.max(offset, 0), maker.makeKotlinData())
        }

        Messages.showMessageDialog(project, "Kotlin Code insert successfully!", "Information", Messages.getInformationIcon())
    }
}


/**
 * do the action that could be roll-back
 */
fun executeCouldRollBackAction(project: Project?, action:(Project?)->Unit) {
    CommandProcessor.getInstance().executeCommand(project, {
        ApplicationManager.getApplication().runWriteAction {
            action.invoke(project)
        }
    }, "insertKotlin", "JsonToKotlin")
}