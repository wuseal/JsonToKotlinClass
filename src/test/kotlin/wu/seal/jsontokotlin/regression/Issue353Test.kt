package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue353Test :BaseTest() {

    @Test
    fun issue353() {
        val json = """
            [[""]]
        """.trimIndent()

        val expectResult = """
            class Test : ArrayList<TestSubList>()

            class TestSubList : ArrayList<String>()
        """.trimIndent()

        TestConfig.apply {
            isPropertiesVar = false
            isCommentOff = true
            isOrderByAlphabetical = true
            propertyTypeStrategy = PropertyTypeStrategy.NotNullable
            defaultValueStrategy = DefaultValueStrategy.None
            targetJsonConvertLib = TargetJsonConverter.None
            isNestedClassModel = false
            enableMapType = false
            enableMinimalAnnotation = false
            parenClassTemplate = ""
        }

       json.generateKotlinClassCode().should.equal(expectResult)
    }
}