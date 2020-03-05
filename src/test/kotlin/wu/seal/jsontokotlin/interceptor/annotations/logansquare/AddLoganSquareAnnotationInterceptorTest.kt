package wu.seal.jsontokotlin.interceptor.annotations.logansquare

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class AddLoganSquareAnnotationInterceptorTest {

    private val json = """{M_property":123}"""

    private val expectedResult = """@JsonObject
data class Test(
    @JsonField(name = arrayOf("M_property""))
    val mProperty: Int // 123
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun intercept() {
        val dataClass = json.generateKotlinDataClass().applyInterceptor(AddLoganSquareAnnotationInterceptor())

        dataClass.getCode().should.be.equal(expectedResult)

    }
}