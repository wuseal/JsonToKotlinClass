package wu.seal.jsontokotlin.interceptor.annotations.moshi

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class AddMoshiAnnotationInterceptorTest {

    private val json = """{M_property":123}"""

    private val expectedResult = """
        data class Test(
    @Json(name = "M_property"")
    val mProperty: Int // 123
)
    """.trimIndent()
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val dataClass = json.generateKotlinDataClass().applyInterceptor(AddMoshiAnnotationInterceptor())

        dataClass.getCode().should.be.equal(expectedResult.trim())
    }
}