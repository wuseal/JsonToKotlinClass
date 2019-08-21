package wu.seal.jsontokotlin.interceptor.annotations.moshi

import com.winterbe.expekt.should
import org.junit.Test

class AddMoshiCodeGenClassImportDeclarationInterceptorTest {

    @Test
    fun intercept() {
        AddMoshiCodeGenClassImportDeclarationInterceptor().intercept("").trim().should.be.equal("""import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass""")
    }
}
