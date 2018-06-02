package wu.seal.jsontokotlin.utils.classblockparse

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

import wu.seal.jsontokotlin.test.TestConfig

class PureNestedClassModelClassesCodeParserTest {

    private val classCode = """data class Data(
    val musicians: List<Musician> = listOf()
) {
    data class Musician(
        val firstName: String = "",
        val lastName: String = "",
        val instrument: String = ""
    )
}"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun parse() {
        val kotlinDataClass = PureNestedClassModelClassesCodeParser(classCode).parse()
        kotlinDataClass.getCode().should.be.equal(classCode)
    }
}