package wu.seal.jsontokotlin.utils.classblockparse

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.*

class ClassCodeParserTest {

    private val tobeParseClassBlockString1="""data class ClassXXX(
    @SerializedName("sa") val sa: Int = 0, // 0
    @SerializedName("Class") val classX: Class = Class()
)"""

    private val tobeParseClassBlockString2="""@Serializable
data class TestData(
    @Optional
    @SerialName("progr ammers")
    val progrAmmers: List<ProgrAmmer> = listOf(),
    @Optional
    @SerialName("aut_hors")
    val autHors: List<AutHor> = listOf(),
    @Optional
    @SerialName("musicians")
    val musicians: List<Musician> = listOf()
)"""

    private val tobeParseClassBlockString3="""data class Class3(
    val programmers: List<Programmer?>? = listOf(),
    val authors: List<Author?>? = listOf(),
    val musicians: List<Musician?>? = listOf()
)"""

    private val tobeParseClassBlockString4="""data class Class3(
    val programmers: List<Programmer>,
    val authors: List<Author>,
    val musicians: List<Musician>
)"""

    private val tobeParseClassBlockString5="""data class Class3(
    val programmers: List<Programmer>, // nothing:yes
    val authors: List<Author>, // :list
    val musicians: List<Musician> // ==list
)"""

    private val tobeParseClassBlockString6 = """data class Data(
    @SerializedName val userID: Int? = 0, // 11
    @SerializedName("name") val name: Name? = Name(),
    @SerializedName("Email", default = "Email") val email: String? = "" // zhuleipro◎hotmail.com
)"""
    private val parser1 = ClassCodeParser(tobeParseClassBlockString1)
    private val parser2 = ClassCodeParser(tobeParseClassBlockString2)
    private val parser3 = ClassCodeParser(tobeParseClassBlockString3)
    private val parser4 = ClassCodeParser(tobeParseClassBlockString4)
    private val parser5 = ClassCodeParser(tobeParseClassBlockString5)
    private val parser6 = ClassCodeParser(tobeParseClassBlockString6)
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getClassName() {
        parser1.getClassName().should.be.equal("ClassXXX")
        parser2.getClassName().should.be.equal("TestData")
        parser3.getClassName().should.be.equal("Class3")
        parser4.getClassName().should.be.equal("Class3")
        parser5.getClassName().should.be.equal("Class3")
        parser6.getClassName().should.be.equal("Data")
    }

    @Test
    fun getClassAnnotation() {
        parser1.getClassAnnotations().should.be.empty
        parser2.getClassAnnotations().should.be.equal(listOf("@Serializable"))
        parser3.getClassAnnotations().should.be.empty
        parser4.getClassAnnotations().should.be.empty
        parser5.getClassAnnotations().should.be.empty
        parser6.getClassAnnotations().should.be.empty
    }

    @Test
    fun getProperties() {
        TestConfig.targetJsonConvertLib = TargetJsonConverter.Gson
        val properties1 = parser1.getProperties()
        properties1.size.should.be.equal(2)
        properties1[0].toString().should.be.equal("""    @SerializedName("sa") val sa: Int = 0, // 0""")
        properties1[1].toString().should.be.equal("""    @SerializedName("Class") val classX: Class = Class()""")


        val properties2 = parser2.getProperties()
        properties2.size.should.be.equal(3)
        properties2[0].toString().should.be.equal("""    @Optional
    @SerialName("progr ammers")
    val progrAmmers: List<ProgrAmmer> = listOf(),""")
        properties2[1].toString().should.be.equal("""    @Optional
    @SerialName("aut_hors")
    val autHors: List<AutHor> = listOf(),""")
        properties2[2].toString().should.be.equal("""    @Optional
    @SerialName("musicians")
    val musicians: List<Musician> = listOf()""")

        val properties3 = parser3.getProperties()
        properties3.size.should.be.equal(3)
        properties3[0].toString().should.be.equal("""    val programmers: List<Programmer?>? = listOf(),""")
        properties3[1].toString().should.be.equal("""    val authors: List<Author?>? = listOf(),""")
        properties3[2].toString().should.be.equal("""    val musicians: List<Musician?>? = listOf()""")

        val properties4 = parser4.getProperties()
        properties4.size.should.be.equal(3)
        properties4[0].toString().should.be.equal("""    val programmers: List<Programmer>,""")
        properties4[1].toString().should.be.equal("""    val authors: List<Author>,""")
        properties4[2].toString().should.be.equal("""    val musicians: List<Musician>""")

        val properties5 = parser5.getProperties()
        properties5.size.should.be.equal(3)
        properties5[0].toString().should.be.equal("""    val programmers: List<Programmer>, // nothing:yes""")
        properties5[1].toString().should.be.equal("""    val authors: List<Author>, // :list""")
        properties5[2].toString().should.be.equal("""    val musicians: List<Musician> // ==list""")


        val properties6 = parser6.getProperties()
        properties6.size.should.be.equal(3)
        properties6[0].toString().should.be.equal("""    @SerializedName val userID: Int? = 0, // 11""")
        properties6[1].toString().should.be.equal("""    @SerializedName("name") val name: Name? = Name(),""")
        properties6[2].toString().should.be.equal("""    @SerializedName("Email", default = "Email") val email: String? = "" // zhuleipro◎hotmail.com""")
    }

    @Test
    fun getKotlinDataClassTest() {
        parser1.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString1)
        parser2.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString2)
        parser3.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString3)
        parser4.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString4)
        parser5.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString5)
        parser6.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString6)
    }

    @Test
    fun testAllConfigParse() {
        val json = """{ "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" }"""
        ConfigManagerTestHelper().testAllConfigWithAction {

            val code = KotlinCodeMaker("ClassName", json).makeKotlinData()
            val kotlinDataClass = ClassCodeParser(code).getKotlinDataClass()
            kotlinDataClass.name.should.be.equal("ClassName")
        }


    }
}
