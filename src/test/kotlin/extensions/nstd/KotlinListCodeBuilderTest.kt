package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.builder.KotlinListCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.ListClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/7/1 14:37.
 */
class KotlinListCodeBuilderTest : ICodeBuilderTest<ListClass> {

    val json = """
        [{"p1":1},{"p2":2}]
    """.trimIndent()

    val expected = """
        class TestList : ArrayList<TestListItem>(){
            data class TestListItem(
                @SerializedName("p1")
                val p1: Int = 0, // 1
                @SerializedName("p2")
                val p2: Int = 0 // 2
            )
        }
    """.trimIndent()

    val expectedCurrent = """
        class TestList : ArrayList<TestListItem>()
    """.trimIndent()

    fun getListClass(): ListClass {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return json.generateKotlinClass("TestList").applyInterceptors(interceptors) as ListClass
    }

    @Before
    override fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testGetCode() {
        KotlinListCodeBuilder(getListClass())
                .getCode().should.be.equal(getExpectedCode())
    }

    @Test
    fun testGetOnlyCurrentCode() {
        KotlinListCodeBuilder(getListClass())
                .getOnlyCurrentCode().should.be.equal(getExpectedCurrentCode())
    }

    override fun getData(): ListClass {
        return getListClass()
    }

    override fun getExpectedCode(): String {
        return expected
    }

    override fun getExpectedCurrentCode(): String {
        return expectedCurrent
    }
}