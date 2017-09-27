package wu.seal.jsontokotlin.statistics

import wu.seal.jsontokotlin.PLUGIN_VERSION
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
fun handlerException(jsonString: String, callBack: () -> Unit): Thread.UncaughtExceptionHandler = Thread.UncaughtExceptionHandler { t, e ->
    val logBuilder = StringBuilder()
    logBuilder.append("PluginVersion:$PLUGIN_VERSION\n")
    logBuilder.append("user: $UUID").append("\n")
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.CHINA).format(Date())
    logBuilder.append("createTime: $time").append("\n")

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
    sendExceptionLog(logBuilder.toString())

    callBack.invoke()
}