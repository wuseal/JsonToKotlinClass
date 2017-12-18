package wu.seal.jsontokotlin

import com.intellij.openapi.components.ApplicationComponent
import wu.seal.jsontokotlin.statistics.sendConfigInfo
import wu.seal.jsontokotlin.statistics.sendHistoryExceptionInfo
import wu.seal.jsontokotlin.statistics.sendHistoryActionInfo

/**
 *
 * Created by Seal.wu on 2017/8/21.
 */

const val PLUGIN_VERSION = "1.5.1"

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
