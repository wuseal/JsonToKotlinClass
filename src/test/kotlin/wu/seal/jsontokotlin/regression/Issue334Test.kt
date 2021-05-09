package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue334Test {

    @Test
    fun testDoubleFormatIssue() {
        TestConfig.setToTestInitState()

        val json="""
           [
             {
               "key": 1.2
             },
             {
               "key": 1
             }
           ]
        """.trimIndent()
        val expectCode = """
            class A : ArrayList<AItem>(){
                data class AItem(
                    @SerializedName("key")
                    val key: Double = 0.0 // 1.2
                )
            }
        """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }

}