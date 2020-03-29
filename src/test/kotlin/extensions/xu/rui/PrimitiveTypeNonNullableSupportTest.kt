package extensions.xu.rui

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.InitWithDefaultValueInterceptor
import wu.seal.jsontokotlin.interceptor.PropertyTypeNullableStrategyInterceptor
import wu.seal.jsontokotlin.test.TestConfig

class PrimitiveTypeNonNullableSupportTest {

    private val json = """{ picture: { "id" : 1, "url" : "" } }"""

    private val expectResult = """data class Test(
    val picture: Picture? = null
) {
    data class Picture(
        val id: Int = 0, // 1
        val url: String? = null
    )
}"""


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.Nullable
        TestConfig.defaultValueStrategy = DefaultValueStrategy.AllowNull
    }

    @Test
    fun interceptTest() {
        val kotlinDataClass = json.generateKotlinDataClass()
        PrimitiveTypeNonNullableSupport.getTestHelper().setConfig("xu.rui.force_primitive_type_non-nullable", "true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(PropertyTypeNullableStrategyInterceptor(), InitWithDefaultValueInterceptor(), PrimitiveTypeNonNullableSupport)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }
}