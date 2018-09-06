package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class AddMoshiCodeGenAnnotationClassInterceptorTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val tobebParsedCode = """data class Data(
    val UserID: Int? = 0, // 11
    val Name: Name? = Name(),
    val Email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptor = AddMoshiCodeGenAnnotationClassInterceptor()
        val interceptedDataClass = interceptor.intercept(MakePropertyOriginNameInterceptor().intercept(kotlinDataClass))
        interceptedDataClass.getCode().should.be.equal(
            """@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "UserID")
    val userID: Int? = 0, // 11
    @Json(name = "Name")
    val name: Name? = Name(),
    @Json(name = "Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)"""
        )
    }
    @Test
    fun interceptWithExistAnnotatio() {
        val tobebParsedCode = """data class Data(
    @SerializedName("UserID") val userID: Int? = 0, // 11
    @SerializedName("name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptor = AddMoshiCodeGenAnnotationClassInterceptor()
        val interceptedDataClass = interceptor.intercept(MakePropertyOriginNameInterceptor().intercept(kotlinDataClass))
        interceptedDataClass.getCode().should.be.equal(
            """@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "UserID")
    val userID: Int? = 0, // 11
    @Json(name = "name")
    val name: Name? = Name(),
    @Json(name = "Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)"""
        )
    }
}