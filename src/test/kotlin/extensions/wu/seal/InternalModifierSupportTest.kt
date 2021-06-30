package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.utils.BaseTest

class InternalModifierSupportTest : BaseTest() {
    private val json = """{"a":1}"""

    @Test
    fun intercept() {
        val expected = """
            internal data class Test(
                val a: Int // 1
            )
        """.trimIndent()
        InternalModifierSupport.getTestHelper().setConfig(InternalModifierSupport.CONFIG_KEY, true.toString())
        json.generateKotlinClass().applyInterceptor(InternalModifierSupport).getCode().should.equal(expected)
        InternalModifierSupport.getTestHelper().setConfig(InternalModifierSupport.CONFIG_KEY, false.toString())
        json.generateKotlinClass().applyInterceptor(InternalModifierSupport)
    }
}