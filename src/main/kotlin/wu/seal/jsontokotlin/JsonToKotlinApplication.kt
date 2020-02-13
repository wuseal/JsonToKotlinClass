package wu.seal.jsontokotlin

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import wu.seal.jsontokotlin.feedback.PLUGIN_VERSION
import wu.seal.jsontokotlin.feedback.sendConfigInfo
import wu.seal.jsontokotlin.feedback.sendHistoryActionInfo
import wu.seal.jsontokotlin.feedback.sendHistoryExceptionInfo
import wu.seal.jsontokotlin.utils.LogUtil

/**
 *
 * Created by Seal.wu on 2017/8/21.
 */

class JsonToKotlinApplication : StartupActivity, DumbAware {

    override fun runActivity(project: Project) {

        LogUtil.i("init JSON To Kotlin Class version ==$PLUGIN_VERSION")

        Thread {
            try {
                sendConfigInfo()
                sendHistoryExceptionInfo()
                sendHistoryActionInfo()
            } catch (e: Exception) {
                LogUtil.e(e.message.toString(),e)
            }
        }.start()
    }
}
