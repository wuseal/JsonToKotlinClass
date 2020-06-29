package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/6/29 17:53.
 */
class ReplaceConstructorParametersByMemberVariablesSupportTest {
    private val json = """{"a":1}"""
    private val expectCode: String = """
        data class Output {
            @SerializedName("a")
            val a: Int = 0 // 1
        }
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        ReplaceConstructorParametersByMemberVariablesSupport.getTestHelper().setConfig(ReplaceConstructorParametersByMemberVariablesSupport.configKey, true.toString())
        json.generateKotlinClassCode("Output").should.be.equal(expectCode)
    }
}