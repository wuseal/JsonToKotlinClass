package wu.seal.jsontokotlin.supporter

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.isTestModel

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class NoneWithCamelCaseSupporterTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    @Test
    fun getNoneLibSupporterClassName() {
        NoneWithCamelCaseSupporter.getNoneLibSupporterClassName("AnyName").should.be.equal("")

    }

    @Test
    fun getNoneLibSupporterProperty() {
        val rawPropertyName = "seal is **() good_man "
        val type = "Boy"
        val block = NoneWithCamelCaseSupporter.getNoneLibSupporterProperty(rawPropertyName, type)
        block.should.contain(type)
        block.should.contain("sealIsGoodMan")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain(" ")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\*")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\(")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("\\)")
        block.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.not.contain("_")
    }

}