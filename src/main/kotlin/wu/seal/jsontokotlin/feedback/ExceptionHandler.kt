package wu.seal.jsontokotlin.feedback

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.openapi.ui.Messages
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */

/**
 * handler the exception
 */

val prettyPrintGson: Gson = GsonBuilder().setPrettyPrinting().create()

fun getUncaughtExceptionHandler(jsonString: String, callBack: () -> Unit): Thread.UncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, e ->
    val logBuilder = StringBuilder()
    logBuilder.append("\n\n")
    logBuilder.append("PluginVersion:$PLUGIN_VERSION\n")
    logBuilder.append("user: $UUID").append("\n")
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.CHINA).format(Date())
    logBuilder.append("createTime: $time").append("\n")

    logBuilder.appendln().append(getConfigInfo()).appendln()
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter, true)
    e.printStackTrace(printWriter)
    var cause = e.cause
    while (cause != null) {
        cause.printStackTrace(printWriter)
        cause = cause.cause
    }
    printWriter.close()
    logBuilder.append(stringWriter.toString())

    logBuilder.append("Error Json String:\n")
    logBuilder.append(jsonString)
    Thread {
        sendExceptionLog(logBuilder.toString())
    }.start()

    callBack.invoke()
}

/**
 * get the config info of this plugin settings
 */
fun getConfigInfo(): String {
    return prettyPrintGson.toJson(ConfigInfo())
}

fun dealWithException(jsonString: String, e: Throwable) {
    var jsonString1 = jsonString
    val yes = Messages.showYesNoDialog("Some thing execute wrong.\nAgree with publishing your JSON text to help us to solve the problem?", "Excuse me", Messages.getQuestionIcon())
    if (yes != Messages.YES) {
        jsonString1 = "User keep private about JSON text"
    }
    getUncaughtExceptionHandler(jsonString1) {
        Messages.showErrorDialog("I am sorry,JsonToKotlinClass may occur a RuntimeException,\nYou could try again after update to the latest version,\nOr you could post an issue here:\nhttps://github.com/wuseal/JsonToKotlinClass\nWe will fixed it soon!", "Occur a fatal error")
    }.uncaughtException(Thread.currentThread(), e)
}