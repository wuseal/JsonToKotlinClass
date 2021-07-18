package extensions.yuan.varenyzc

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClass
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

class BuildFromJsonObjectSupportTest {
    private val json = """{"a":1,"b":"abc","c":true}"""

    private val expectCode: String = """
        data class Test(
            val a: Int, // 1
            val b: String, // abc
            val c: Boolean // true
        ) {
            companion object {
                @JvmStatic
                fun buildFromJson(jsonObject: JSONObject?): Test? {

                    jsonObject?.run {
                        return Test(
                            optInt("a"),
                            optString("b"),
                            optBoolean("c")
                        )
                    }
                    return null
                }
            }
        }
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        BuildFromJsonObjectSupport.getTestHelper().setConfig(BuildFromJsonObjectSupport.configKey, true.toString())
        val applyInterceptor = json.generateKotlinClass().applyInterceptor(BuildFromJsonObjectSupport)
        applyInterceptor.getCode().should.be.equal(expectCode)
    }
}