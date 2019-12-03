package extensions.chen.biao

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * @author chenbiao
 * create at 2019/5/17
 * description:
 */
class KeepAnnotationSupportTest {

    val json = """{"name":"chenbiao"}"""


    var expectResult = """@Keep
data class Test(
    val name: String // chenbiao
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest(){
        val kotlinDataClass = json.generateKotlinDataClass()
        KeepAnnotationSupport.getTestHelper().setConfig("chen.biao.add_keep_annotation_enable","true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(KeepAnnotationSupport)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }

}