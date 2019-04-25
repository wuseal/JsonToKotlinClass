package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.applyInterceptor
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class InitWithDefaultValueInterceptorTest {

    private val json = """{"a":123}"""

    private val expected = """data class Test(
    val a: Int = 0 // 123
)"""


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(InitWithDefaultValueInterceptor())


        dataClass.getCode().should.be.equal(expected)
    }
}