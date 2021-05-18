package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.utils.BaseTest

class ClassNamePrefixSupportTest : BaseTest() {

    private val json = """
        {
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
        }
    """.trimIndent()

    private val expectedResult = """
        data class DtoTest(
            val a: Int, // 1
            val b: DtoB
        ) {
            data class DtoB(
                val c: List<Int>,
                val d: List<DtoD>
            ) {
                data class DtoD(
                    val e: Int // 0
                )
            }
        }
    """.trimIndent()

    @Test
    fun intercept() {
        val kotlinDataClass =
            json.generateKotlinDataClass()
        ClassNameSuffixSupport.getTestHelper().setConfig(ClassNamePrefixSupport.prefixKeyEnable, "true")
        ClassNameSuffixSupport.getTestHelper().setConfig(ClassNamePrefixSupport.prefixKey, "Dto")
        val generatedCode = kotlinDataClass.applyInterceptor(ClassNamePrefixSupport).getCode()
        generatedCode.should.equal(expectedResult)
    }
}