package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test

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
}
