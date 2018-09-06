package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*

class AddMoshiCodeGenClassDeclarationInterceptorTest {

    @Test
    fun intercept() {
        AddMoshiCodeGenClassDeclarationInterceptor().intercept("").should.be.equal("""import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass""")
    }
}