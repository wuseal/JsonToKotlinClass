package wu.seal.jsontokotlin.server.utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 文件相关处理类
 * Created by Seal.Wu on 2017/9/26.
 */
private val usrHome = System.getProperty("user.home")

private val rootDirPath = "$usrHome/jsontokotlin/"

private val rootDir = {
    val rootDir = File(rootDirPath)
    if (!rootDir.exists()) {
        rootDir.mkdirs()
    }
    rootDir
}.invoke()

private val exceptionLogDir = {
    val exceptionDir = File(rootDir, "exceptions")
    if (!exceptionDir.exists()) {
        exceptionDir.mkdirs()
    }
    exceptionDir
}.invoke()

fun writeExceptionLog(exceptionInfo: String) {
    val day = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
    val dayDir = File(exceptionLogDir, day)
    if (!dayDir.exists()) {
        dayDir.mkdirs()
    }
    File(dayDir, Date().time.toString()).writeText(exceptionInfo)
}