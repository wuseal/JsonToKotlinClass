package wu.seal.jsontokotlin

import com.intellij.openapi.components.ApplicationComponent
import wu.seal.jsontokotlin.feedback.PLUGIN_VERSION
import wu.seal.jsontokotlin.feedback.sendConfigInfo
import wu.seal.jsontokotlin.feedback.sendHistoryActionInfo
import wu.seal.jsontokotlin.feedback.sendHistoryExceptionInfo
import wu.seal.jsontokotlin.utils.LogUtil

/**
 *
 * Created by Seal.wu on 2017/8/21.
 */

class JsonToKotlinApplication : ApplicationComponent {

    override fun initComponent() {

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

    override fun disposeComponent() {}

    override fun getComponentName(): String {
        return "wu.seal.jsontokotlin.JsonToKotlinApplication"
    }
}
