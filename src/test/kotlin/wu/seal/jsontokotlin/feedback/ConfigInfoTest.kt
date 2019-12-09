package wu.seal.jsontokotlin.feedback

import com.google.gson.Gson
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class ConfigInfoTest {

    @Before
    fun before() {
        isTestModel = true
    }

    @Test
    fun toConfigInfoToJson() {
        val info = ConfigInfo()
        val infoJson = Gson().toJson(info)
        infoJson.should.not.be.empty
        println(infoJson)
    }

    @Test
    fun getConfigTest() {
        println(getConfigInfo())
    }
}