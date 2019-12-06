package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue243Test {

    @Test
    fun testIssue243() {
        TestConfig.setToTestInitState()
        val json="""
            [
              "I'm outside",
              "I'm here",
              "I can't find your house",
              "Im coming",
              "Be there soon"
            ]
        """.trimIndent()
        val expectCode = """class A : ArrayList<String>()""".trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData()
        resultCode.should.be.equal(expectCode)
    }
}