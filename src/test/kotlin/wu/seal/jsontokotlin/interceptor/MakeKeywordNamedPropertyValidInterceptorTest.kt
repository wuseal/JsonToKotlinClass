package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class MakeKeywordNamedPropertyValidInterceptorTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val toBeParsedCode = """data class (val in: String,
            val as: Int,
            val dupa: Double
)"""
        val kotlinDataClass = KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(toBeParsedCode).getKotlinDataClass())
        val intercepted = MakeKeywordNamedPropertyValidInterceptor().intercept(kotlinDataClass)
        intercepted.getCode().should.be.equal("""data class (
    val `in`: String,
    val `as`: Int,
    val dupa: Double
)""")
    }
}