package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class EnumTest {
    @Before
    fun setUp() {
        TestConfig.setToTestInitStateForJsonSchema()
    }

    @Test
    fun testNumericEnum() {
        val json = """{
            "${"$"}ref": "#/definitions/Container",
            "${"$"}schema": "http://json-schema.org/draft-07/schema#",
            "definitions": {
                "Container": {
                    "additionalProperties": false,
                    "properties": {
                        "score": {
                            "${"$"}ref": "#/definitions/Score"
                        }
                    },
                    "required": [
                        "score"
                    ],
                    "type": "object"                
                },
                "Score": {
                    "enum": [
                        1,
                        2,
                        3,
                        4
                    ],
                    "type": "number"
                }
            }
        }""".trimIndent()

        val expected = """   
                   
data class Test(
    val score: Score
) {
    enum class Score(val value: Double) {
        _1(1.0),

        _2(2.0),

        _3(3.0),

        _4(4.0);
    }
}
""".trim()

        val result = json.generateKotlinClass("Test").getCode()
        result.trim().should.be.equal(expected)

    }
}