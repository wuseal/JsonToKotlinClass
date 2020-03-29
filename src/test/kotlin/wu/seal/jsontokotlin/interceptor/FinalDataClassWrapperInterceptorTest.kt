package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class FinalDataClassWrapperInterceptorTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        with("""{"in":"","as":1,"dupa":1.0,"1ab":99999999999999,"ab_c${"$"}d":"","":1}""") {
            generateKotlinDataClass("A").applyInterceptors(listOf(CommentOffInterceptor, FinalKotlinClassWrapperInterceptor()))
        }.getCode().should.be.equal("""data class A(
    val `in`: String,
    val `as`: Int,
    val dupa: Double,
    val `1ab`: Long,
    val `ab_c${"$"}d`: String,
    val : Int
)""")
    }
}
