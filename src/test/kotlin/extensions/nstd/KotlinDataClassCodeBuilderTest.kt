package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.builder.KotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/7/1 14:33.
 */
class KotlinDataClassCodeBuilderTest : ICodeBuilderTest<DataClass>{

    val json = """{"a":1,"b":{"c":"string"}}"""

    val expectCode: String = """
        data class Output(
            val a: Int, // 1
            val b: B
        ) {
            data class B(
                val c: String // string
            )
        }
    """.trimIndent()

    val expectCurrentCode: String = """
        data class Output(
            val a: Int, // 1
            val b: B
        )
    """.trimIndent()

    @Before
    override fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testGetCode() {
        val clazz = getData()
        KotlinDataClassCodeBuilder
                .getCode(clazz).should.be.equal(getExpectedCode())
    }

    @Test
    fun testGetOnlyCurrentCode() {
        KotlinDataClassCodeBuilder
                .getOnlyCurrentCode(getData()).should.be.equal(getExpectedCurrentCode())
    }

    override fun getData(): DataClass {
        return json.generateKotlinDataClass("Output")
    }

    override fun getExpectedCode(): String {
        return expectCode
    }

    override fun getExpectedCurrentCode(): String {
        return expectCurrentCode
    }
}