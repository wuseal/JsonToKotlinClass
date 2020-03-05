package wu.seal.jsontokotlin.model.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

import wu.seal.jsontokotlin.test.TestConfig

class ListClassTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getOnlyCurrentCode() {
        val expected = "class ListTest : ArrayList<Any>()"
        val listClass = ListClass("ListTest", KotlinClass.ANY)
        listClass.getOnlyCurrentCode().should.equal(expected)

        val dataClassProperty = Property(name = "p1", originName = "p1", type = "String", typeObject = KotlinClass.STRING)
        val generic = DataClass(name = "Data", properties = listOf(dataClassProperty))
        val listClass2 = ListClass("ListTest", generic)
        val expected2 = """
            class ListTest : ArrayList<Data>()
        """.trimIndent()
        listClass2.getOnlyCurrentCode().should.equal(expected2)
    }

    @Test
    fun replaceReferencedClasses() {
        var listClass = ListClass("ListTest", KotlinClass.ANY)
        listClass = listClass.replaceReferencedClasses(mapOf(KotlinClass.ANY to KotlinClass.DOUBLE))
        listClass.generic.should.be.equal(KotlinClass.DOUBLE)
        listClass.referencedClasses.size.should.be.equal(1)
        listClass.referencedClasses[0].should.be.equal(KotlinClass.DOUBLE)
    }

    @Test
    fun rename() {
        val listClass = ListClass("ListTest", KotlinClass.ANY)
        val listClassNew = listClass.rename("ListTestNew")
        listClassNew.name.should.be.equal("ListTestNew")
    }

    @Test
    fun getCode() {
        val dataClassProperty = Property(name = "p1", originName = "p1", type = "String", typeObject = KotlinClass.STRING)
        val generic = DataClass(name = "Data", properties = listOf(dataClassProperty))
        val listClass = ListClass("ListTest", generic)
        val expected = """
            class ListTest : ArrayList<Data>(){
                data class Data(
                    val p1: String
                )
            }
        """.trimIndent()
        listClass.getCode().should.equal(expected)

    }

    @Test
    fun getReferencedClasses() {
        val listClass = ListClass("ListTest", KotlinClass.ANY)
        listClass.referencedClasses.size.should.be.equal(1)
        listClass.referencedClasses[0].should.be.equal(KotlinClass.ANY)

        val dataClassProperty = Property(name = "p1", originName = "p1", type = "String", typeObject = KotlinClass.STRING)
        val generic = DataClass(name = "Data", properties = listOf(dataClassProperty))
        val listClass2 = ListClass("ListTest", generic)
        listClass2.referencedClasses.size.should.be.equal(1)
        listClass2.referencedClasses[0].should.be.equal(generic)
    }
}