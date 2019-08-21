package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.KotlinDataClassCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue195Test {

    private val expected = """data class A(
    @SerializedName("xxx")
    val xxx: List<List<Any>> = listOf()
)"""

    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    /**
     * test issue #130 of Github Project issue
     */
    @Test
    fun testIssue195() {
        val generated = KotlinDataClassCodeMaker("A", "{\"xxx\":[[]]}").makeKotlinDataClassCode()
        generated.trim().should.be.equal(expected)
    }
}