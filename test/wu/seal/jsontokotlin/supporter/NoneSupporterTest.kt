package wu.seal.jsontokotlin.supporter

import com.winterbe.expekt.should
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 * None support json converter test
 * Created by Seal.Wu on 2018/2/6.
 */
class NoneSupporterTest {

    @Before
    fun before() {
        isTestModel = true
    }

    @Test
    fun getNoneLibSupporterClassName() {
        val className = "seal is **() good_man "
        val result = NoneSupporter.getNoneLibSupporterClassName(className)
        assertTrue(result == "")


    }

    @Test
    fun getNoneLibSupporterProperty() {
        val rawPropertyName = "seal is **() good_man "
        val propertyType = "Boy"

        val propertyBlock = NoneSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType)
        propertyBlock.split(":").last().substringBefore("=").trim().should.be.equal(propertyType)
        propertyBlock.substringAfter(KPropertyKeyword.get()).substringBefore(":").trim().should.be.equal(rawPropertyName.trim())

    }

}