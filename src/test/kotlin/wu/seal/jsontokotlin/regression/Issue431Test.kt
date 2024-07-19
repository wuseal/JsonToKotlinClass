package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest
import wu.seal.jsontokotlin.utils.resetJsonToKotlinRandom

class Issue431Test : BaseTest() {
    @Test
    fun testIssue431() {
        TestConfig.apply {
            isCommentOff = true
            targetJsonConvertLib = TargetJsonConverter.None
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = false
        }
        val json = """{"list":[{}]}"""
        val expected = """
        data class Test(
            val list: List<Item0>
        )
        
        class Item0
        """.trimIndent()
        resetJsonToKotlinRandom()
        json.generateKotlinClassCode("Test").should.equal(expected)
    }
}
