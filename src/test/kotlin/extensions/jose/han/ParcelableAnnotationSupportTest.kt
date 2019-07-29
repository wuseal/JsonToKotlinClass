package extensions.jose.han

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.test.TestConfig

/**
 * @author jose.han
 * @Date 2019/04/27
 */
class ParcelableAnnotationSupportTest{
    val json = """{"name":"jose.han","age":18,"height":18.7, "face":true}"""


    var expectResult = """@SuppressLint("ParcelCreator")
@Parcelize
data class Test(
    val name: String, // jose.han
    val age: Int, // 18
    val height: Double, // 18.7
    val face: Boolean // true
) : Parcelable
"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest(){
        val kotlinDataClass = json.generateKotlinDataClass()
        ParcelableAnnotationSupport.getTestHelper().setConfig("jose.han.add_parcelable_annotatioin_enable","true")
        val generatedCode = kotlinDataClass.applyInterceptors(listOf(ParcelableAnnotationSupport)).getCode()
        print(generatedCode)
        generatedCode.trimMargin().should.equal(expectResult.trimMargin())

    }


}