package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinDataClassCodeMaker
import wu.seal.jsontokotlin.KotlinDataClassMaker
import wu.seal.jsontokotlin.test.TestConfig


/**
 * Created by karthi
 *
 **/
class Issue225Test {

    private val testJson = """
    [{"a":0,"b":""},{"a":null,"b":null}]
    """.trimIndent()

    private val expected = """data class A(
    @SerializedName("a")
    val a: Int = 0, // 0
    @SerializedName("b")
    val b: String = ""
)"""



    @Test
    fun testIssue225() {
        TestConfig.setToTestInitState()
        val generated = KotlinDataClassCodeMaker(KotlinDataClassMaker("A",testJson).makeKotlinDataClass()).makeKotlinDataClassCode()
        generated.should.be.equal(expected)
    }

}