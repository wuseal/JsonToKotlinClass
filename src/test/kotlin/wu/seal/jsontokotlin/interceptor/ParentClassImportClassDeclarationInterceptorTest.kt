package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.test.TestConfig

class ParentClassImportClassDeclarationInterceptorTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun intercept() {
        ConfigManager.parenClassTemplate = "java.io.Serializable"
        val originImportCode = ""
        val interceptedCode = ParentClassImportClassDeclarationInterceptor().intercept(originImportCode)
        interceptedCode.should.equal("import java.io.Serializable")

    }
}