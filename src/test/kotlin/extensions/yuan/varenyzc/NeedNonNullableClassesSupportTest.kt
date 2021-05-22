package extensions.yuan.varenyzc

import com.winterbe.expekt.should
import extensions.nstd.ReplaceConstructorParametersByMemberVariablesSupport
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

class NeedNonNullableClassesSupportTest {
    private val json = """{"a":1,"b":"abc","c":true}"""
    private val expectCode: String = """
        data class Test(
            val a: Int, // 1
            val b: String?, // abc
            val c: Boolean // true
        )
    """.trimIndent()

    private val nonNullableClasses = "Int,Boolean"

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val kotlinDataClass = json.generateKotlinClass()
        NeedNonNullableClassesSupport.getTestHelper()
            .setConfig(NeedNonNullableClassesSupport.prefixKeyEnable, true.toString())
        NeedNonNullableClassesSupport.getTestHelper()
            .setConfig(NeedNonNullableClassesSupport.prefixKey, nonNullableClasses)
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(NeedNonNullableClassesSupport)).getCode()
        generatedCode.trimMargin().should.equal(expectCode.trimMargin())
    }
}