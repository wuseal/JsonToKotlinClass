package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Test

import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.utils.BaseTest

class AddGsonExposeAnnotationTest : BaseTest() {
    private val json = """{"a":1}"""

    val expected = """
        data class Test(
            @Expose
            val a: Int // 1
        )
    """.trimIndent()
    @Test
    fun intercept() {
        AddGsonExposeAnnotationSupport.testHelper.setConfig(AddGsonExposeAnnotationSupport.config_key, true.toString())
        val result = json.generateKotlinClass().applyInterceptor(AddGsonExposeAnnotationSupport).getCode()
        result.should.be.equal(expected)
    }

    @Test
    fun testIntercept() {
        AddGsonExposeAnnotationSupport.testHelper.setConfig(AddGsonExposeAnnotationSupport.config_key, true.toString())
        AddGsonExposeAnnotationSupport.intercept("").should.equal("\nimport com.google.gson.annotations.Expose")
    }
}