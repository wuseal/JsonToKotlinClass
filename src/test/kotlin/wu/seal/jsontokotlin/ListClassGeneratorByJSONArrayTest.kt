package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Before
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.ListClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classgenerator.ListClassGeneratorByJSONArray

class ListClassGeneratorByJSONArrayTest {


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun generateBaseListTypeTest() {
        ListClassGeneratorByJSONArray("TestList", "[]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.ANY))
        ListClassGeneratorByJSONArray("TestList", "[1]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.INT))
        ListClassGeneratorByJSONArray("TestList", "[1.0]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.DOUBLE))
        ListClassGeneratorByJSONArray("TestList", "[true]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.BOOLEAN))
        ListClassGeneratorByJSONArray("TestList", "[100000000000000]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.LONG))
        ListClassGeneratorByJSONArray("TestList", "[null]").generate()
                .should.be.equal(ListClass("TestList", KotlinClass.ANY))
    }

    @Test
    fun generateListClassWithDataClass() {
        val result = ListClassGeneratorByJSONArray("TestList", "[{p1:1}]").generate()
        val dataClassProperty = Property(name = "p1",originName = "p1",type = "Int",comment = "1",originJsonValue = "1",typeObject = KotlinClass.INT)
        val itemClass = DataClass(name = "TestListItem",properties = listOf(dataClassProperty))
        result.should.be.equal(ListClass("TestList", itemClass))
    }

    @Test
    fun generateListClassWithListClass() {
        val result = ListClassGeneratorByJSONArray("TestList", "[[]]").generate()
        result.should.be.equal(ListClass("TestList", ListClass("TestListSubList", KotlinClass.ANY)))
    }
}