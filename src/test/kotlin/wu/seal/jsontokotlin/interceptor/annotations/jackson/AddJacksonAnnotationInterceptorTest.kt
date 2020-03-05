package wu.seal.jsontokotlin.interceptor.annotations.jackson

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.annotations.logansquare.AddLoganSquareAnnotationInterceptor
import wu.seal.jsontokotlin.test.TestConfig

class AddJacksonAnnotationInterceptorTest {

    private val json = """{M_property":123}"""

    private val expectedResult = """data class Test(
    @JsonProperty("M_property"")
    val mProperty: Int // 123
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(AddJacksonAnnotationInterceptor())

        dataClass.getCode().should.be.equal(expectedResult)

    }
}