package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue425Test : BaseTest() {
    @Test
    fun testIssue425() {
        val json = """
            {
              "firstTeam": {
                "hometown": {
                  "name": "Town 1"
                },
                "stats": {
                  "rating": 10
                }
              },
              "secondTeam": {
                "hometown": {
                  "name": "Town 2"
                },
                "stats": {
                  "rating": 20
                }
              }
            }
        """.trimIndent()
        val expected = """
            data class Match(
                val firstTeam: FirstTeam,
                val secondTeam: SecondTeam
            )
            
            data class FirstTeam(
                val hometown: Hometown,
                val stats: Stats
            )
            
            data class SecondTeam(
                val hometown: Hometown,
                val stats: Stats
            )
            
            data class Hometown(
                val name: String
            )
            
            data class Stats(
                val rating: Int
            )
        """.trimIndent()
        TestConfig.apply {
            isCommentOff = true
            targetJsonConvertLib = TargetJsonConverter.None
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = false
        }
        json.generateKotlinClassCode("Match").should.equal(expected)
    }
}
