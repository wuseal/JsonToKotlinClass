package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue354Test : BaseTest() {

    @Test
    fun issue354() {
        val json = """
            {
              "objList": [
                {
                  "a": 1
                }
              ],
              "obj": {
                "a": 1
              }
            }
        """.trimIndent()

        val expectResult = """
            data class Test(
                val obj: Obj,
                val objList: List<Obj>
            )

            data class Obj(
                val a: Int
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

        json.generateKotlinClassCode().should.equal(expectResult)
    }

    @Test
    fun issue354WhenInnerClass() {
        val json = """
            {
              "objList": [
                {
                  "a": 1
                }
              ],
              "obj": {
                "a": 1
              }
            }
        """.trimIndent()

        val expectResult = """
            data class Test(
                val obj: Obj,
                val objList: List<Obj>
            ) {
                data class Obj(
                    val a: Int
                )
            }
        """.trimIndent()

        TestConfig.apply {
            isPropertiesVar = false
            isCommentOff = true
            isOrderByAlphabetical = true
            propertyTypeStrategy = PropertyTypeStrategy.NotNullable
            defaultValueStrategy = DefaultValueStrategy.None
            targetJsonConvertLib = TargetJsonConverter.None
            isNestedClassModel = true
            enableMapType = false
            enableMinimalAnnotation = false
            parenClassTemplate = ""
            extensionsConfig = ""
        }

        json.generateKotlinClassCode().should.equal(expectResult)
    }
}