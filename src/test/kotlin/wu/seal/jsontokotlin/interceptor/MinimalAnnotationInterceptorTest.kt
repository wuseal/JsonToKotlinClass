package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.applyInterceptor
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationClassImportDeclarationInterceptor
import wu.seal.jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationInterceptor

import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

class MinimalAnnotationInterceptorTest {

    private val json ="""
        {
            "a": 1,
             "a_b": 2,
             "a c": 3
        }
    """.trimIndent()

    private val excepted = """data class Test(
    val a: Int = 0, // 1
    @SerializedName("a c")
    val aC: Int = 0, // 3
    @SerializedName("a_b")
    val aB: Int = 0 // 2
)"""
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        val generateKotlinDataClass = json.generateKotlinDataClass()
        val interceptedDataClass = generateKotlinDataClass.applyInterceptor(AddGsonAnnotationInterceptor())
        .applyInterceptor(MinimalAnnotationKotlinDataClassInterceptor())

        interceptedDataClass.getCode().should.be.equal(excepted)
    }
}

