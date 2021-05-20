package extensions.yuan.varenyzc

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

class BuildFromJsonObjectSupportTest {
    private val json = """{"a":1,"b":"abc","c":true}"""

    private val expectCode: String = """
        data class Test(
            @SerializedName("a")
            val a: Int = 0, // 1
            @SerializedName("b")
            val b: String = "", // abc
            @SerializedName("c")
            val c: Boolean = false // true
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
        print(json.generateKotlinClassCode())
        json.generateKotlinClassCode().should.be.equal(expectCode)
        BuildFromJsonObjectSupport.getTestHelper().setConfig(BuildFromJsonObjectSupport.configKey, false.toString())
        json.generateKotlinClassCode()
    }
}