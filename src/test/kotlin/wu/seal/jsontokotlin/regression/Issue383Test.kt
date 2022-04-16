package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import extensions.wu.seal.ForceInitDefaultValueWithOriginJsonValueSupport
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.utils.BaseTest

class Issue383Test : BaseTest() {

    @Test
    fun issue383() {
        val json = """
            {"a":"${"$"}\\/aaa"}
        """.trimIndent()

        val expected = """
            data class Test(
                val a: String = ""${'"'}${"$"}{"$"}\/aaa""${'"'} // ${'$'}\/aaa
            )
        """.trimIndent()

        ForceInitDefaultValueWithOriginJsonValueSupport.apply {
            testHelper.setConfig(configKey, true.toString())
        }
        json.generateKotlinDataClass("Test").applyInterceptor(ForceInitDefaultValueWithOriginJsonValueSupport)
            .getCode().should.equal(expected)
    }
}
