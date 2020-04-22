package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig

class ParentClassTemplateDataClassInterceptorTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        ConfigManager.parenClassTemplate = "java.io.Serializable()"
        val json = """{a:2}"""
        val kotlinDataClass =
            KotlinClassMaker("Test", json).makeKotlinClass() as DataClass
        val interceptedClass = ParentClassTemplateKotlinClassInterceptor().intercept(kotlinDataClass)
        interceptedClass.getCode().should.be.equal(
            """data class Test(
    val a: Int // 2
) : Serializable()"""
        )
    }
}