package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.model.builder.KotlinListCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.ListClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Nstd on 2020/7/1 14:37.
 */
class KotlinListCodeBuilderTest : ICodeBuilderTest<ListClass> {

    @Before
    override fun setUp() {
        TestConfig.setToTestInitState()
    }

    val expected = """
        class TestList : ArrayList<TestListItem>(){
            data class TestListItem(
                @SerializedName("p1")
                val p1: Int = 0 // 1
            )
        }
    """.trimIndent()

    val expectedCurrentCode = """
        class TestList : ArrayList<TestListItem>()
    """.trimIndent()

    fun getListClass(): ListClass {
        val dataClassProperty = Property(name = "p1",originName = "p1",type = "Int",comment = "1",originJsonValue = "1",typeObject = KotlinClass.INT)
        val itemClass = DataClass(name = "TestListItem",properties = listOf(dataClassProperty))
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return ListClass("TestList", itemClass).applyInterceptors(interceptors) as ListClass
    }

    @Test
    fun testGetCode() {
        KotlinListCodeBuilder(getListClass())
                .getCode().should.be.equal(expected)
    }

    @Test
    fun testGetOnlyCurrentCode() {
        KotlinListCodeBuilder(getListClass())
                .getOnlyCurrentCode().should.be.equal(expectedCurrentCode)
    }

    override fun getData(): ListClass {
        return getListClass()
    }

    override fun getExpectedCode(): String {
        return expected
    }
}