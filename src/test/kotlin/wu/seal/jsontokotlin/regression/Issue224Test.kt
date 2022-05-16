package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.*
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Seal.Wu on 2019-08-31
 * Description: Test Case For Issue 224
 */
class Issue224Test {
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun test() {
        val json = """
        {
            "${"$"}schema": "http://json-schema.org/draft-06/schema#",
            "definitions": {
                "address": {
                    "properties": {
                        "city": {
                            "type": "string"
                        },
                        "state": {
                            "type": "string"
                        },
                        "street_address": {
                            "type": "string"
                        }
                    },
                    "required": [
                        "street_address",
                        "city",
                        "state"
                    ],
                    "type": "object"
                }
            },
            "properties": {
                "billing_address": {
                    "${"$"}ref": "#/definitions/address"
                },
                "shipping_address": {
                    "${"$"}ref": "#/definitions/address"
                }
            },
            "type": "object"
        }
        """.trimIndent()

        val expect = """
            data class Test(
                val billing_address: address = address(),
                val shipping_address: address = address()
            )

            data class address(
                val city: String = "",
                val state: String = "",
                val street_address: String = ""
            )
        """.trimIndent()
        TestConfig.isNestedClassModel = false
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isCommentOff = true
        KotlinCodeMaker("Test", json).makeKotlinData().should.be.equal(expect)
    }

}