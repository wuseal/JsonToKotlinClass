package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Seal.Wu on 2019/11/9
 * Description:
 */
class Issue249Test {

    @Test
    fun testIssue249() {
        TestConfig.setToTestInitState()
        val json="""{"mobile.agree_with_conditions":"yes"}"""
        val expectCode = """
            data class A(
                @SerializedName("mobile.agree_with_conditions")
                val mobileAgreeWithConditions: String = "" // yes
            )
            """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }
}