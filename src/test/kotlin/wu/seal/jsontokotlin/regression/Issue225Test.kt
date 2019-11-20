package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinClassCodeMaker
import wu.seal.jsontokotlin.KotlinClassMaker
import wu.seal.jsontokotlin.test.TestConfig


/**
 * Created by karthi
 *
 **/
class Issue225Test {

    private val testJson = """
    [{"a":0,"b":""},{"a":null,"b":null}]
    """.trimIndent()

    private val expected = """
                class A : ArrayList<AItem>(){
                    data class AItem(
                        @SerializedName("a")
                        val a: Int = 0, // 0
                        @SerializedName("b")
                        val b: String = ""
                    )
                }
    """.trimIndent()



    @Test
    fun testIssue225() {
        TestConfig.setToTestInitState()
        val generated = KotlinClassCodeMaker(KotlinClassMaker("A", testJson).makeKotlinDataClass()).makeKotlinClassCode()
        generated.trimIndent().should.be.equal(expected)
    }

}
