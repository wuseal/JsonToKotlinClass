package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class MinimalAnnotationInterceptorTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val tobebParsedCode = """data class Data(
    @SerializedName("UserID") val userID: Int? = 0, // 11
    @SerializedName("name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptor = MinimalAnnotationKotlinDataClassInterceptor()
        val interceptedDataClass = interceptor.intercept(kotlinDataClass)
        interceptedDataClass.getCode().should.be.equal("""data class Data(
    @SerializedName("UserID")
    val userID: Int? = 0, // 11
    val name: Name? = Name(),
    @SerializedName("Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)""")

    }

    @Test
    fun intercept2() {
        val tobebParsedCode = """data class Data(
    @SerializedName val userID: Int? = 0, // 11
    @SerializedName("name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptor = MinimalAnnotationKotlinDataClassInterceptor()
        val interceptedDataClass = interceptor.intercept(kotlinDataClass)
        interceptedDataClass.getCode().should.be.equal("""data class Data(
    @SerializedName
    val userID: Int? = 0, // 11
    val name: Name? = Name(),
    @SerializedName("Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)""")

    }

    @Test
    fun intercept3() {
        val tobebParsedCode = """data class Data(
    @SerializedName val userID: Int? = 0, // 11
    @SerializedName("name") val name: Name? = Name(),
    @SerializedName("Email", default = "Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        ConfigManager.customPropertyAnnotationFormatString ="@SerializedName(\"%s\", default = \"%s\")"
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptor = MinimalAnnotationKotlinDataClassInterceptor()
        val interceptedDataClass = interceptor.intercept(kotlinDataClass)
        interceptedDataClass.getCode().should.be.equal("""data class Data(
    @SerializedName
    val userID: Int? = 0, // 11
    val name: Name? = Name(),
    @SerializedName("Email", default = "Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)""")

    }
}

