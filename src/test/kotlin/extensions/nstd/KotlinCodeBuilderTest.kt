package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.builder.KotlinCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/7/1 14:33.
 */
class KotlinCodeBuilderTest : ICodeBuilderTest<DataClass>{

    val json = """{"a":1,"b":{"c":"string"}}"""

    val expectCode: String = """
        data class Output(
            @SerializedName("a")
            val a: Int = 0, // 1
            @SerializedName("b")
            val b: B = B()
        ) {
            data class B(
                @SerializedName("c")
                val c: String = "" // string
            )
        }
    """.trimIndent()

    val expectCurrentCode: String = """
        data class Output(
            @SerializedName("a")
            val a: Int = 0, // 1
            @SerializedName("b")
            val b: B = B()
        )
    """.trimIndent()

    @Before
    override fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testGetCode() {
        KotlinCodeBuilder(getData())
                .getCode().should.be.equal(getExpectedCode())
    }

    @Test
    fun testGetOnlyCurrentCode() {
        KotlinCodeBuilder(getData())
                .getOnlyCurrentCode().should.be.equal(getExpectedCurrentCode())
    }

    override fun getData(): DataClass {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return json.generateKotlinClass("Output").applyInterceptors(interceptors) as DataClass
    }

    override fun getExpectedCode(): String {
        return expectCode
    }

    override fun getExpectedCurrentCode(): String {
        return expectCurrentCode
    }
}