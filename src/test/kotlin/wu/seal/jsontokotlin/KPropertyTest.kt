package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import wu.seal.jsontokotlin.codeelements.KProperty
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 *
 * Created by Seal.Wu on 2017/11/1.
 */
class KPropertyTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    /**
     * test the config and choose respective supporter logic is OK
     */
    @org.junit.Test
    fun getPropertyStringBlock() {

        val property = KProperty("seal is a *() good_man", "Boolean", "true")

        val propertyStringBlock = property.getPropertyStringBlock()

        propertyStringBlock.trim().should.be.equal("""val seal is a *() good_man: Boolean""")

    }

}