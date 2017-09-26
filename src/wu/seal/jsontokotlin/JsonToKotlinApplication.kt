package wu.seal.jsontokotlin

import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.ui.Messages
import wu.seal.jsontokotlin.statistics.handlerException
import wu.seal.jsontokotlin.statistics.sendHistoryExceptionInfo
import wu.seal.jsontokotlin.statistics.sendHistoryActionInfo

/**
 *
 * Created by Seal.wu on 2017/8/21.
 */
class JsonToKotlinApplication : ApplicationComponent {

    override fun initComponent() {

        println("init json to kotlin ")
        Thread() {
            sendHistoryExceptionInfo()
            sendHistoryActionInfo()
        }.start()
    }

    override fun disposeComponent() {}

    override fun getComponentName(): String {
        return "wu.seal.wu.seal.jsontokotlin.JsonToKotlinApplication"
    }
}
