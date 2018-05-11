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
fun getIndent(): String {

    return buildString {

        for (i in 1..ConfigManager.indent) {
            append(" ")
        }
    }
}

/**
 * get class string block list from a big string which contains classes
 */
fun getClassesStringList(classesString: String): List<String> {
    return classesString.split("\n\n").filter { it.isNotBlank() }
}

/**
 * export the class name from class block string
 */
fun getClassNameFromClassBlockString(classBlockString: String): String {
    return classBlockString.substringAfter("data class").substringBefore("(").trim()
}

fun replaceClassNameToClassBlockString(classBlockString: String, newClassName: String): String {
    val blockPre = classBlockString.substringBefore("data class")
    val blockAfter = classBlockString.substringAfter("(")
    val blockMid = "data class $newClassName("
    return blockPre + blockMid + blockAfter
}

fun <E, K, V> List<E>.toMap(converter: (E) -> Pair<K, V>): Map<K, V> {
    val map = mutableMapOf<K, V>()
    forEach {
        val converterResult = converter(it)
        map[converterResult.first] = converterResult.second
    }
    return map
}