package wu.seal.jsontokotlin.interceptor

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class FinalKotlinDataClassWrapperInterceptorTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
//        val toBeParsedCode = """data class A(val in: String,
//            val as: Int,
//            val dupa: Double,
//            val 1ab: Long,
//            val ab_c$""" + 'd' + """: String,
//            val : Int
//)"""
//        val kotlinDataClass = KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(toBeParsedCode).getKotlinDataClass())
//        val intercepted = FinalKotlinDataClassWrapperInterceptor().intercept(kotlinDataClass)
//        intercepted.getCode().should.be.equal("""data class A(
//    val `in`: String,
//    val `as`: Int,
//    val dupa: Double,
//    val `1ab`: Long,
//    val `ab_c$"""+"d`"+""": String,
//    val : Int
//)""")
    }
}