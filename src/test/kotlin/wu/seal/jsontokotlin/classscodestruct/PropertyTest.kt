package wu.seal.jsontokotlin.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.codeannotations.GsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.test.TestConfig

class PropertyTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getCode() {
        val normalProperty = Property(listOf(), "val", name = "name", type = "Type", isLast = false, originJsonValue = "", originName = "", typeObject = KotlinClass.ANY)
        normalProperty.getCode().should.be.equal("""val name: Type""")
        val withValueProperty = Property(listOf(), "val", name = "name", type = "Type", value = "Type()", isLast = false, originJsonValue = "", originName = "", typeObject = KotlinClass.ANY)
        withValueProperty.getCode().should.be.equal("""val name: Type = Type()""")

        val withValueAndAnnotationProperty = Property(GsonPropertyAnnotationTemplate("name").getAnnotations(), "val", name = "name", type = "Type", value = "Type()", isLast = false, originJsonValue = "", originName = "", typeObject = KotlinClass.ANY)
        withValueAndAnnotationProperty.getCode().should.be.equal("""@SerializedName("name")
val name: Type = Type()""".trimIndent())
    }
}