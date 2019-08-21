package wu.seal.jsontokotlin.feedback

import java.net.HttpURLConnection
import java.net.URL

/**
 * NetWork relative
 * Created by Seal.Wu on 2017/9/25.
 */

const val actionInfoUrl = "https://jsontokotlin.sealwu.com:8443/sendActionInfo"
const val exceptionLogUrl = "https://jsontokotlin.sealwu.com:8443/sendExceptionInfo"
const val configLogUrl = "https://jsontokotlin.sealwu.com:8443/sendConfigInfo"
//const val actionInfoUrl = "http://localhost:8008/sendActionInfo"
//const val exceptionLogUrl = "http://localhost:8008/sendExceptionInfo"
//const val configLogUrl = "http://localhost:8008/sendConfigInfo"

fun sendExceptionLog(log: String) {
    sendData(exceptionLogUrl, log)
}


fun sendActionInfo(actionInfo: String) {
    sendData(actionInfoUrl, actionInfo)
}

fun sendConfigInfo() {
    sendData(configLogUrl, getConfigInfo())
}

fun sendHistoryExceptionInfo() {
    Thread {
        PersistCache.readAllCachedExceptionInfo().split("#").filter { it.isNotBlank() }.forEach {
            sendExceptionLog(it)
        }
    }.start()
    PersistCache.deleteAllExceptionInfoCache()
}


fun sendHistoryActionInfo() {
    Thread {
        PersistCache.readAllCachedActionInfo().split("#").filter { it.isNotBlank() }.forEach {
            sendActionInfo(it)
        }
    }.start()
    PersistCache.deleteAllActionInfo()
}

fun sendData(url: String, log: String) {
    Thread {
        try {
            with(URL(url).openConnection() as HttpURLConnection) {
                when (url) {
                    actionInfoUrl, configLogUrl -> {
                        doOutput = true
                        requestMethod = "POST"
                        addRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    }

                    exceptionLogUrl -> {
                        doOutput = true
                        doInput = true
                        addRequestProperty("Content-Type", "application/text")
                    }
                }
                outputStream.use {
                    val writer = it.writer()
                    writer.write(log)
                    writer.flush()
                }
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    val a = responseMessage + "\n" + errorStream.use { it.reader().readText() }
                    println(a)
                }
                disconnect()
            }
        } catch (e: Exception) {

            e.printStackTrace()

            when (url) {

                actionInfoUrl -> PersistCache.saveActionInfo(log)

                exceptionLogUrl -> PersistCache.saveExceptionInfo(log)

                else -> Unit//Do nothing
            }
        }
    }.start()
}
