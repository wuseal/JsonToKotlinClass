package wu.seal.jsontokotlin.classscodestruct

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class KotlinDataClassTest {

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getCode() {
        val normalProperty = Property(listOf(), "val", name = "name", type = "Type",  isLast = false, originJsonValue = "",originName = "")
        val withValueProperty = Property(listOf(), "val", name = "name", type = "Type", value = "Type()", comment = "comment", isLast = true, originJsonValue = "",originName = "")
        val properties = listOf(normalProperty, withValueProperty)
        val dataClass = KotlinDataClass(-1, listOf(), "name", properties = properties)
        dataClass.getCode().should.be.equal(
            """data class name(
    val name: Type,
    val name: Type = Type() // comment
)"""
        )
    }
}