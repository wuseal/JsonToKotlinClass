package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/6/29 17:53.
 */
class ReplaceConstructorParametersByMemberVariablesSupportTest {
    private val json = """{"a":1,"b":2}"""
    private val expectCode: String = """
        data class Output {
            val a: Int // 1
            val b: Int // 2
        }
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        ReplaceConstructorParametersByMemberVariablesSupport.getTestHelper().setConfig(ReplaceConstructorParametersByMemberVariablesSupport.configKey, true.toString())
        json.generateKotlinClass("Output").applyInterceptor(ReplaceConstructorParametersByMemberVariablesSupport).getCode().should.be.equal(expectCode)
    }
}