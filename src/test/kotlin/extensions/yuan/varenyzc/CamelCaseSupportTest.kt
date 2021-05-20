package extensions.yuan.varenyzc

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

class CamelCaseSupportTest {
    private val json = """{"data_a":1,"data_b":"123","data_c":true}"""
    private val expertCode: String = """
        data class Test(
            val dataA: Int, // 1
            val dataB: String, // 123
            val dataC: Boolean // true
        )
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val kotlinDataClass = json.generateKotlinClass()
        CamelCaseSupport.getTestHelper().setConfig(CamelCaseSupport.configKey, true.toString())
        val generatedCode = kotlinDataClass.applyInterceptor(CamelCaseSupport).getCode()
        generatedCode.trimMargin().should.be.equal(expertCode.trimMargin())
    }
}