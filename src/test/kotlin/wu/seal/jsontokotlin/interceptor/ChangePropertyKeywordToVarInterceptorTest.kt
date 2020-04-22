package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class ChangePropertyKeywordToVarInterceptorTest {

    private val json = """{"a":123}"""


    private val expected = """data class Test(
    var a: Int // 123
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(ChangePropertyKeywordToVarInterceptor())

        dataClass.getCode().should.be.equal(expected)
    }
}