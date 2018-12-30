package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by kezhenxu at 2018/12/30 11:45
 *
 * @author kezhenxu (kezhenxu94 at 163 dot com)
 */
class Issue094Test {
    private val testJson = """
    { "test": {} }
    """.trimIndent()
    private val expected = """
data class A(
    @SerializedName("test") val test: Test = Test()
) {
    
    class Test(
    )
}"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testIssue087() {
        KotlinCodeMaker("A", testJson).makeKotlinData().should.be.equal(expected)
    }
}
