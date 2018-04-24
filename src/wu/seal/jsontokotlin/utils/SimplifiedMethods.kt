package wu.seal.jsontokotlin.utils

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.project.Project
import wu.seal.jsontokotlin.ConfigManager

/**
 * File contains functions which simply other functions's invoke
 * Created by Seal.Wu on 2018/2/7.
 */


/**
 * do the action that could be roll-back
 */
fun executeCouldRollBackAction(project: Project?, action: (Project?) -> Unit) {
    CommandProcessor.getInstance().executeCommand(project, {
        ApplicationManager.getApplication().runWriteAction {
            action.invoke(project)
        }
    }, "insertKotlin", "JsonToKotlin")
}

/**
 * get the indent when generate kotlin class code
 */
fun getIndent() :String{

    return buildString {

        for (i in 1..ConfigManager.indent) {
            append(" ")
        }
    }
}