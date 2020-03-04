package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.test.TestConfig

class ParentClassClassImportDeclarationInterceptorTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }
    @Test
    fun intercept() {
        ConfigManager.parenClassTemplate = "java.io.Serializable"
        val originImportCode = ""
        val interceptedCode = ParentClassClassImportDeclarationInterceptor().intercept(originImportCode)
        interceptedCode.should.equal("import java.io.Serializable")

    }
}