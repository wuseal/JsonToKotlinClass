package extensions.jose.han

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * @auther jose.han
 */
class FileHeaderSupportTest {

    val json = """{"user":"jose"}"""


    var expectResult = """/**
 * Created by Lenovo on 2020/8/29.
 */

data class Test(
    val user: String // jose
)"""


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest(){
        val kotlinDataClass = json.generateKotlinDataClass()
        FileHeaderSupport.getTestHelper().setConfig("jose.han.file_template","true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(FileHeaderSupport)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }
}