package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.utils.BaseTest

class AnalyticsSwitchSupportTest : BaseTest() {

    @Test
    fun enableAnalytics() {
        val originalValue = AnalyticsSwitchSupport.enableAnalytics()
        val expectedValue = true
        AnalyticsSwitchSupport.getTestHelper()
            .setConfig(AnalyticsSwitchSupport.configKey, expectedValue.toString())
        val nowValue = AnalyticsSwitchSupport.enableAnalytics()
        nowValue.should.be.equal(expectedValue)
        AnalyticsSwitchSupport.getTestHelper()
            .setConfig(AnalyticsSwitchSupport.configKey, originalValue.toString())
    }
}