package wu.seal.jsontokotlin.interceptor.annotations.moshi

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class AddMoshiCodeGenAnnotationInterceptorTest {

    private val json ="""
        {
            "a": 1,
             "a_b": 2,
             "a c": 3
        }
    """.trimIndent()

    private val excepted = """@JsonClass(generateAdapter = true)
data class Test(
    @Json(name = "a")
    val a: Int, // 1
    @Json(name = "a_b")
    val aB: Int, // 2
    @Json(name = "a c")
    val aC: Int // 3
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        val interceptedClass = json.generateKotlinDataClass("Test").applyInterceptor(AddMoshiCodeGenAnnotationInterceptor())

        interceptedClass.getCode().should.be.equal(excepted)
    }
}