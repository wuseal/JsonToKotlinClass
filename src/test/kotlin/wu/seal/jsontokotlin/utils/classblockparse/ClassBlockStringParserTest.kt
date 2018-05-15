package wu.seal.jsontokotlin.utils.classblockparse

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig

class ClassBlockStringParserTest {

    val tobeParseClassBlockString1="""data class ClassXXX(
    @SerializedName("sa") val sa: Int = 0, // 0
    @SerializedName("Class") val classX: Class = Class()
)"""

    val tobeParseClassBlockString2="""@Serializable
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

    val tobeParseClassBlockString3="""data class Class3(
    val programmers: List<Programmer?>? = listOf(),
    val authors: List<Author?>? = listOf(),
    val musicians: List<Musician?>? = listOf()
)"""

    val tobeParseClassBlockString4="""data class Class3(
    val programmers: List<Programmer>,
    val authors: List<Author>,
    val musicians: List<Musician>
)"""
    val parser1 = ClassBlockStringParser(tobeParseClassBlockString1)
    val parser2 = ClassBlockStringParser(tobeParseClassBlockString2)
    val parser3 = ClassBlockStringParser(tobeParseClassBlockString3)
    val parser4 = ClassBlockStringParser(tobeParseClassBlockString4)
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
    }

    @Test
    fun getClassAnnotation() {
        parser1.getClassAnnotations().should.be.empty
        parser2.getClassAnnotations().should.be.equal(listOf("@Serializable"))
        parser3.getClassAnnotations().should.be.empty
        parser4.getClassAnnotations().should.be.empty
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
    }

    @Test
    fun getKotlinDataClassTest() {
        parser1.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString1)
        parser2.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString2)
        parser3.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString3)
        parser4.getKotlinDataClass().toString().should.be.equal(tobeParseClassBlockString4)
    }
}