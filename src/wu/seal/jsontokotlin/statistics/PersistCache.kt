package wu.seal.jsontokotlin.statistics

import java.io.File
import java.util.*

/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */

interface IPersistCache {

    fun saveExceptionInfo(exceptionInfo: String)

    fun readAllCachedExceptionInfo(): String

    fun deleteAllExceptionInfoCache()

    fun saveActionInfo(actionInfo: String)

    fun readAllCachedActionInfo(): String

    fun deleteAllActionInfo()
}

fun main(args: Array<String>) {
    PersistCache.saveExceptionInfo("Exception")
    println(PersistCache.readAllCachedExceptionInfo())
}

object PersistCache : IPersistCache {

    private val usrHome = System.getProperty("user.home")
    private val rootDir = "$usrHome/.jsontokotlin"
    private val exceptionDirPath = "$rootDir/exceptionLog"

    private val actionInfoPath = "$rootDir/actionInfo"

    private val exceptionDir = File(exceptionDirPath)
    private val actionDir = File(actionInfoPath)

    init {

        if (!exceptionDir.exists()) {
            exceptionDir.mkdirs()
        }

        if (!actionDir.exists()) {
            actionDir.mkdirs()
        }
    }

    override fun saveExceptionInfo(exceptionInfo: String) {
        File(exceptionDir, Date().time.toString()).writeText(exceptionInfo)
    }

    override fun readAllCachedExceptionInfo(): String {
        val builder = StringBuilder()
        exceptionDir.listFiles().forEach {
            builder.append(it.readText()).append("#")
        }
        return builder.toString()
    }

    override fun deleteAllExceptionInfoCache() {
        exceptionDir.listFiles().forEach { it.delete() }
    }

    override fun saveActionInfo(actionInfo: String) {
        File(actionDir, Date().time.toString()).writeText(actionInfo)
    }

    override fun readAllCachedActionInfo(): String {
        val builder = StringBuilder()
        actionDir.listFiles().forEach {
            builder.append(it.readText()).append("#")
        }
        return builder.toString()
    }

    override fun deleteAllActionInfo() {
    }

}