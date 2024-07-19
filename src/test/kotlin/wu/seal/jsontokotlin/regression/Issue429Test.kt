package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue429Test : BaseTest() {
    @Test
    fun testIssue429() {
        TestConfig.apply {
            isCommentOff = true
            targetJsonConvertLib = TargetJsonConverter.None
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = false
        }
        val json1 = """["hi", null, "hi"]"""
        val expected1 = "class Test : ArrayList<String?>()"
        json1.generateKotlinClassCode("Test").should.equal(expected1)
        val json2 = """[{}, null, {}]"""
        val expected2 = """
            class Test : ArrayList<TestItem?>()
            
            class TestItem
        """.trimIndent()
        json2.generateKotlinClassCode("Test").should.equal(expected2)
    }
}