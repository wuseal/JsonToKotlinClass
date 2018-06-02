package wu.seal.jsontokotlin.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class KotlinDataClassTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getCode() {
        val normalProperty = Property(listOf(), "val", "name", "Type", "", "", false)
        val withValueProperty = Property(listOf(), "val", "name", "Type", "Type()", "comment", true)
        val properties = listOf(normalProperty, withValueProperty)
        val dataClass = KotlinDataClass(-1, listOf(), "name", properties, listOf())
        dataClass.getCode().should.be.equal(
            """data class name(
    val name: Type,
    val name: Type = Type() // comment
)"""
        )
    }

    @Test
    fun toParsedKotlinDataClass() {
        val normalProperty = Property(listOf(), "val", "name", "Type", "", "", false)
        val withValueProperty = Property(listOf(), "val", "name", "Type", "Type()", "comment", true)
        val properties = listOf(normalProperty, withValueProperty)
        val dataClass = KotlinDataClass(-1, listOf(), "name", properties, listOf())
        val parsedKotlinDataClass = dataClass.toParsedKotlinDataClass()
        parsedKotlinDataClass.annotations.should.be.empty
        parsedKotlinDataClass.name.should.be.equal(dataClass.name)
        parsedKotlinDataClass.toString().should.be.equal(dataClass.getCode())
    }

    @Test
    fun fromParsedKotlinDataClass() {
        val tobebParsedCode = """data class Data(
    @SerializedName("UserID") val userID: Int? = 0, // 11
    @SerializedName("Name") val name: Name? = Name(),
    @SerializedName("Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
        val kotlinDataClass =
            KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(tobebParsedCode).getKotlinDataClass())
        kotlinDataClass.getCode().should.be.equal("""data class Data(
    @SerializedName("UserID")
    val userID: Int? = 0, // 11
    @SerializedName("Name")
    val name: Name? = Name(),
    @SerializedName("Email")
    val email: String? = "" // zhuleipro◎hotmail.com
)""")

    }
}