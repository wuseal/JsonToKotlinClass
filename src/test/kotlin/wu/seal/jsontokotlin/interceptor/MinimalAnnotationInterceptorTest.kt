package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationInterceptor
import wu.seal.jsontokotlin.test.TestConfig

class MinimalAnnotationInterceptorTest {

    private val json ="""
        {
            "a": 1,
             "a_b": 2,
             "a c": 3
        }
    """.trimIndent()

    private val excepted = """data class Test(
    val a: Int, // 1
    @SerializedName("a_b")
    val aB: Int, // 2
    @SerializedName("a c")
    val aC: Int // 3
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val generateKotlinDataClass = json.generateKotlinDataClass()
        val interceptedDataClass = generateKotlinDataClass.applyInterceptor(AddGsonAnnotationInterceptor())
        .applyInterceptor(MinimalAnnotationKotlinClassInterceptor())

        interceptedDataClass.getCode().should.be.equal(excepted)
    }
}

