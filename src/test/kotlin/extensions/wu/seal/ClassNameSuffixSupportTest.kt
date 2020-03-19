package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.InitWithDefaultValueInterceptor
import wu.seal.jsontokotlin.interceptor.PropertyTypeNullableStrategyInterceptor
import wu.seal.jsontokotlin.test.TestConfig

class ClassNameSuffixSupportTest {

    private val suffixKeyEnable = "wu.seal.class_name_suffix_enable"
    private val suffixKey = "wu.seal.class_name_suffix"

    val json = """{
  "a": 1,
  "b": {
    "c": [
      1
    ],
    "d": [
      {
        "e": 0
      }
    ]
  }
}"""


    val expectResult = """data class TestDto(
    val a: Int, // 1
    val b: BDto
) {
    data class BDto(
        val c: List<Int>,
        val d: List<DDto>
    ) {
        data class DDto(
            val e: Int // 0
        )
    }
}"""
    val expectResultWithDefaultValueAvoidNull = """data class TestDto(
    val a: Int = 0, // 1
    val b: BDto = BDto()
) {
    data class BDto(
        val c: List<Int> = listOf(),
        val d: List<DDto> = listOf()
    ) {
        data class DDto(
            val e: Int = 0 // 0
        )
    }
}"""

    val expectResultWithDefaultValueAllowNull = """data class TestDto(
    val a: Int? = null, // 1
    val b: BDto? = null
) {
    data class BDto(
        val c: List<Int?>? = null,
        val d: List<DDto?>? = null
    ) {
        data class DDto(
            val e: Int? = null // 0
        )
    }
}"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val kotlinDataClass =
            json.generateKotlinDataClass()

        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKeyEnable, "true")
        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKey, "Dto")
        val generatedCode = kotlinDataClass.applyInterceptor(ClassNameSuffixSupport).getCode()

        generatedCode.trimMargin().should.equal(expectResult.trimMargin())
    }


    @Test
    fun interceptWithDefaultValueOptionEnableAvoidNull() {

        TestConfig.defaultValueStrategy = DefaultValueStrategy.AvoidNull

        val kotlinDataClass =
            json.generateKotlinDataClass()

        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKeyEnable, "true")
        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKey, "Dto")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(InitWithDefaultValueInterceptor(),ClassNameSuffixSupport)).getCode()

        println(generatedCode)
        generatedCode.trimMargin().should.equal(expectResultWithDefaultValueAvoidNull.trimMargin())

    }

    @Test
    fun interceptWithDefaultValueOptionEnableAllowNull() {

        TestConfig.defaultValueStrategy = DefaultValueStrategy.AllowNull
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.Nullable

        val kotlinDataClass =
            json.generateKotlinDataClass()

        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKeyEnable, "true")
        ClassNameSuffixSupport.getTestHelper().setConfig(suffixKey, "Dto")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(PropertyTypeNullableStrategyInterceptor(),InitWithDefaultValueInterceptor(),ClassNameSuffixSupport)).getCode()

        println(generatedCode)
        generatedCode.trimMargin().should.equal(expectResultWithDefaultValueAllowNull.trimMargin())

    }
    @Test
    fun testIsMapType() {
        "".isMapType().should.be.`false`
        "Map".isMapType().should.be.`false`
        "Map<String>".isMapType().should.be.`false`
        "Map<String,String>".isMapType().should.be.`true`
    }

    private fun String.isMapType(): Boolean {

        return matches(Regex("Map<.+,.+>"))
    }
}
