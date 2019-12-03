package extensions.ted.zeng

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.interceptor.annotations.fastjson.AddFastJsonAnnotationInterceptor
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by ted on 2019-06-13 18:21.
 */
class PropertyAnnotationLineSupportTest {
    private val json = """{"a":"a","Int":2}"""
    private val expectResult = """data class Test(
    @JSONField(name = "a") val a: String, // a
    @JSONField(name = "Int") val int: Int // 2
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun interceptTest() {
        val kotlinDataClass = json.generateKotlinDataClass()
        PropertyAnnotationLineSupport.getTestHelper().setConfig("ted.zeng.property_annotation_in_same_line_enable", "true")
        val result = kotlinDataClass.applyInterceptors(listOf(AddFastJsonAnnotationInterceptor(), PropertyAnnotationLineSupport)).getCode()
        result.should.equal(expectResult)
    }

    @Test
    fun finalClassCodeTest() {
        val expectResult = """data class Test(
    @SerializedName("a") val a: String = "", // a
    @SerializedName("Int") val int: Int = 0 // 2
)"""
        PropertyAnnotationLineSupport.getTestHelper().setConfig("ted.zeng.property_annotation_in_same_line_enable", "true")
        val resultCode = KotlinCodeMaker("Test", json).makeKotlinData()
        resultCode.should.be.equal(expectResult)
    }
}