package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue357Test : BaseTest() {

    @Test
    fun issue357() {
        val json = """
            {
              "page": {
                "href": "a"
              },
              "_page": {
                "href": "a"
              }
            }
        """.trimIndent()

        val expected = """
            data class Test(
                val _page: Page,
                val page: Page
            )

            data class Page(
                val href: String
            )
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
            extensionsConfig = ""
        }

        json.generateKotlinClassCode().should.equal(expected)
    }
}