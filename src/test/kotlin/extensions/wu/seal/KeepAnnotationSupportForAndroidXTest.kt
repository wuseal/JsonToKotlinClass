package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * @author Seal.Wu
 * create at 2019/11/03
 * description:
 */
class KeepAnnotationSupportForAndroidXTest {

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
        KeepAnnotationSupportForAndroidX.getTestHelper().setConfig("wu.seal.add_keep_annotation_enable_androidx","true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(KeepAnnotationSupportForAndroidX)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }

}