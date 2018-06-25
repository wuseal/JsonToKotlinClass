package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class ParentClassTemplateKotlinDataClassInterceptorTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        ConfigManager.parenClassTemplate = "java.io.Serializable()"
        val tobebParsedCode = """data class Data(
    @SerializedName("UserID") val userID: Int? = 0, // 11
    @SerializedName("Name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        val interceptedClass = ParentClassTemplateKotlinDataClassInterceptor().intercept(kotlinDataClass)
        interceptedClass.getCode().should.be.equal(
            """data class Data(
    @SerializedName("UserID")
    val userID: Int? = 0, // 11
    @SerializedName("Name")
    val name: Name? = Name(),
    @SerializedName("Email")
    val email: String? = "" // zhuleipro◎hotmail.com
) : Serializable()"""
        )
    }
}