package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
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
        AddGsonExposeAnnotation.testHelper.setConfig(AddGsonExposeAnnotation.config_key, true.toString())
        val result = json.generateKotlinClass().applyInterceptor(AddGsonExposeAnnotation).getCode()
        result.should.be.equal(expected)
    }

    @Test
    fun testIntercept() {
        AddGsonExposeAnnotation.testHelper.setConfig(AddGsonExposeAnnotation.config_key, true.toString())
        AddGsonExposeAnnotation.intercept("").should.equal("\nimport com.google.gson.annotations.Expose")
    }
}