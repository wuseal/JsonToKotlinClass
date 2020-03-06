package extensions.wu.seal

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig

class DisableDataClassSupportTest {
    private val json = """{"a":1}"""
    private val expectCode: String = """
        class Output(
            @SerializedName("a")
            val a: Int = 0 // 1
        )
    """.trimIndent()

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {
        DisableDataClassSupport.getTestHelper().setConfig(DisableDataClassSupport.configKey, true.toString())
        json.generateKotlinClassCode("Output").should.be.equal(expectCode)
    }
}