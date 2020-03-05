package wu.seal.jsontokotlin.model.classscodestruct

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.test.TestConfig

class GenericListClassTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun replaceReferencedClasses() {
        val genericListClass = GenericListClass(generic = KotlinClass.ANY)
        val newGenericListClass = genericListClass.replaceReferencedClasses(mapOf(KotlinClass.BOOLEAN to KotlinClass.DOUBLE, KotlinClass.ANY to KotlinClass.BOOLEAN))
        newGenericListClass.generic.should.be.equal(KotlinClass.BOOLEAN)
    }
}