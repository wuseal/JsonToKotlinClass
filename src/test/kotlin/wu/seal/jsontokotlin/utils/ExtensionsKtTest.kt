package wu.seal.jsontokotlin.utils

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*

class ExtensionsKtTest {

    @Test
    fun numberOf() {
        val s = "I am a %s boy yes you yes you yes !"
        s.numberOf("%s").should.be.equal(1)
        s.numberOf("yes").should.be.equal(3)
    }

    @Test
    fun arrayOfNullTest() {
        val count = 5
        val array = arrayOfNulls<String>(count)
        array.fill("Seal")
        array.forEach {
            it.should.be.equal("Seal")
        }
    }
}