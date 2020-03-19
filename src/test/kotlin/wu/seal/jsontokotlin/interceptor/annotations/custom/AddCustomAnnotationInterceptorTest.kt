package wu.seal.jsontokotlin.interceptor.annotations.custom

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class AddCustomAnnotationInterceptorTest {


    private val json = """{M_property":123}"""

    private val expectedDataClass = """@Serializable
data class Test(
    @SerialName("M_property"")
    val mProperty: Int // 123
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }


    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(AddCustomAnnotationInterceptor())

        dataClass.getCode().should.be.equal(expectedDataClass)
    }
}