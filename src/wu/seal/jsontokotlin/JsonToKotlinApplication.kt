package wu.seal.jsontokotlin

import com.intellij.openapi.components.ApplicationComponent
import wu.seal.jsontokotlin.feedback.sendConfigInfo
import wu.seal.jsontokotlin.feedback.sendHistoryExceptionInfo
import wu.seal.jsontokotlin.feedback.sendHistoryActionInfo

/**
 *
 * Created by Seal.wu on 2017/8/21.
 */

const val PLUGIN_VERSION = "1.7"

class JsonToKotlinApplication : ApplicationComponent {

    override fun initComponent() {

        println("init json to kotlin ")
        Thread() {
            sendConfigInfo()
            sendHistoryExceptionInfo()
            sendHistoryActionInfo()
        }.start()
    }

    override fun disposeComponent() {}

    override fun getComponentName(): String {
        return "wu.seal.jsontokotlin.JsonToKotlinApplication"
    }
}
