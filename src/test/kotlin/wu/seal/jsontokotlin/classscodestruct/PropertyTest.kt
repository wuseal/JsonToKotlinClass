package wu.seal.jsontokotlin.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.codeannotations.GsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class PropertyTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getCode() {
        val normalProperty = Property(listOf(), "val", "name", "Type", "", "", false, originJsonValue = "",originName = "")
        normalProperty.getCode().should.be.equal("""val name: Type""")
        val withValueProperty = Property(listOf(), "val", "name", "Type", "Type()", "", false, originJsonValue = "",originName = "")
        withValueProperty.getCode().should.be.equal("""val name: Type = Type()""")

        val withValueAndAnnotationProperty = Property(GsonPropertyAnnotationTemplate("name").getAnnotations(), "val", "name", "Type", "Type()", "", false, originJsonValue = "",originName = "")
        withValueAndAnnotationProperty.getCode().should.be.equal("""@SerializedName("name")
val name: Type = Type()""".trimIndent())
    }

    @Test
    fun toParsedProperty() {
        val normalProperty = Property(listOf(), "val", "name", "Type", "", "", false, originJsonValue = "",originName = "")

        val normalParsedProperty = normalProperty.toParsedProperty()

        normalParsedProperty.annotations.should.be.empty

        normalParsedProperty.keyword.should.be.equal(normalProperty.keyword)
        normalParsedProperty.propertyName.should.be.equal(normalProperty.name)
        normalParsedProperty.propertyType.should.be.equal(normalProperty.type)
        normalParsedProperty.propertyValue.should.be.equal(normalProperty.value)
        normalParsedProperty.propertyComment.should.be.equal(normalProperty.comment)
        normalParsedProperty.isLastProperty.should.be.equal(normalProperty.isLast)

        val withValueProperty = Property(
                GsonPropertyAnnotationTemplate("name").getAnnotations(),
                "val",
                "name",
                "Type",
                "Type()",
                "",
                false,
                originJsonValue = "",
                originName = ""
        )
        val parsedWithValueProperty = withValueProperty.toParsedProperty()

        parsedWithValueProperty.annotations.size.should.be.equal(1)

        parsedWithValueProperty.annotations[0].should.be.equal("""@SerializedName("name")""")

        parsedWithValueProperty.keyword.should.be.equal(withValueProperty.keyword)
        parsedWithValueProperty.propertyName.should.be.equal(withValueProperty.name)
        parsedWithValueProperty.propertyType.should.be.equal(withValueProperty.type)
        parsedWithValueProperty.propertyValue.should.be.equal(withValueProperty.value)
        parsedWithValueProperty.propertyComment.should.be.equal(withValueProperty.comment)
        parsedWithValueProperty.isLastProperty.should.be.equal(withValueProperty.isLast)

    }

    @Test
    fun fromParsedProperty() {
        val classCode = """data class Data(
    @SerializedName("UserID") val userID: Int? = 0, // 11
    @SerializedName("Name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        ClassCodeParser(classCode).getProperties().forEach {
            Property.fromParsedProperty(it).type.should.be.equal(it.propertyType)
            Property.fromParsedProperty(it).name.should.be.equal(it.propertyName)
            Property.fromParsedProperty(it).comment.should.be.equal(it.propertyComment)
            Property.fromParsedProperty(it).keyword.should.be.equal(it.keyword)
            Property.fromParsedProperty(it).value.should.be.equal(it.propertyValue)
        }
    }
}