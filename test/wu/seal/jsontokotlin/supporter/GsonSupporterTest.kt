package wu.seal.jsontokotlin.supporter

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.isTestModel

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class GsonSupporterTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    @Test
    fun getGsonSupporterProperty() {
        val rawPropertyName = "seal is **() good_man "
        val type = "Boy"
        val block = GsonSupporter.getGsonSupporterProperty(rawPropertyName, type)
        block.should.contain(rawPropertyName)
        block.should.contain(type)

        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain(" ")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\*")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\(")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\)")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("_")
    }

}