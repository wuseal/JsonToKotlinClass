package wu.seal.jsontokotlin.interceptor.annotations.fastjson

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class AddFastJsonAnnotationInterceptorTest {

    private val json = """{M_property":123}"""

    private val expectedDataClass = """data class Test(
    @JSONField(name = "M_property"")
    val mProperty: Int // 123
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(AddFastJsonAnnotationInterceptor())

        dataClass.getCode().should.be.equal(expectedDataClass)

    }
}