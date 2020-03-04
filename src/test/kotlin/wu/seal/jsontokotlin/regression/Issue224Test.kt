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
            "${"$$"}schema": "http://json-schema.org/draft-06/schema#",
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
            val `${"$$"}schema`: String = "",
            val definitions: Definitions = Definitions(),
            val properties: PropertiesX = Properties(),
            val type: String = ""
        )
        
        data class Definitions(
            val address: Address = Address()
        )

        data class PropertiesX(
            val billing_address: BillingAddress = BillingAddress(),
            val shipping_address: ShippingAddress = ShippingAddress()
        )
        
        data class Address(
            val properties: Properties = Properties(),
            val required: List<String> = listOf(),
            val type: String = ""
        )
        
        data class Properties(
            val city: City = City(),
            val state: State = State(),
            val street_address: StreetAddress = StreetAddress()
        )
        
        data class City(
            val type: String = ""
        )
        
        data class State(
            val type: String = ""
        )
        
        data class StreetAddress(
            val type: String = ""
        )
        
        data class BillingAddress(
            val `${"$"}ref`: String = ""
        )
        
        data class ShippingAddress(
            val `${"$"}ref`: String = ""
        )
        """.trimIndent()
        TestConfig.isNestedClassModel = false
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isCommentOff = true
        KotlinCodeMaker("Test", json).makeKotlinData().should.be.equal(expect)
    }

}