package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.utils.toJavaDocMultilineComment

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class StringTest {

    @Test
    fun substringAfterTest() {
        val s = "yes,I do"
        val result1 = s.substringAfter(",")
        result1.should.be.equal("I do")
        val result2 = s.substringAfter("AM")
        result2.should.be.equal(s)
    }

    @Test
    fun substringBeforeTest() {
        val s = "yes,I do"
        val result1 = s.substringBefore(",")
        result1.should.be.equal("yes")
        val result2 = s.substringBefore("AM")
        result2.should.be.equal(s)
    }

    @Test
    fun `toJavaDocMultilineComment must return empty string`() {
        val result = " \t\n  ".toJavaDocMultilineComment()
        result.should.empty
    }

    @Test
    fun `toJavaDocMultilineComment must wrap into Javadoc`() {
        val testJson = """
            {
              "a": "str",
              "b": [
                {
                  "c": 1
                },
                {
                  "c": 2
                }
              ]
            }
        """.trimIndent()
        val result = testJson.toJavaDocMultilineComment()

        result.should.startWith("/**\n")
        result.should.endWith("*/\n")
        result.should.contain(testJson)
    }
}
