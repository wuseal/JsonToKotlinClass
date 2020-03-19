package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class OrderPropertyByAlphabeticalInterceptorTest {

    private val json = """{"c":123,"a":2,"b":3}"""


    private val expected = """data class Test(
    val a: Int, // 2
    val b: Int, // 3
    val c: Int // 123
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun intercept() {

        val dataClass = json.generateKotlinDataClass().applyInterceptor(OrderPropertyByAlphabeticalInterceptor())

        dataClass.getCode().should.be.equal(expected)
    }
}