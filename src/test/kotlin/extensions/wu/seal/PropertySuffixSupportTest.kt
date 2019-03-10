package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class PropertySuffixSupportTest {

    val json = """{"a":1}"""


    val expectResult = """data class Test(
    val aWu: Int // 1
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest() {
        val kotlinDataClass =
                json.generateKotlinDataClass()
        PropertySuffixSupport.getTestHelper().setConfig("wu.seal.property_suffix_enable","true")
        PropertySuffixSupport.getTestHelper().setConfig("wu.seal.property_suffix","wu")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(PropertySuffixSupport)).getCode()

        generatedCode.trimMargin().should.equal(expectResult.trimMargin())
    }

}