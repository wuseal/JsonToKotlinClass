package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

class PropertyPrefixSupportTest {
    val json = """{"a":1}"""


    val expectResult = """data class Test(
    val sealA: Int // 1
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest() {
        val kotlinDataClass =
                json.generateKotlinDataClass()
        PropertyPrefixSupport.getTestHelper().setConfig("wu.seal.property_prefix_enable","true")
        PropertyPrefixSupport.getTestHelper().setConfig("wu.seal.property_prefix","seal")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(PropertyPrefixSupport)).getCode()

        generatedCode.trimMargin().should.equal(expectResult.trimMargin())
    }
}