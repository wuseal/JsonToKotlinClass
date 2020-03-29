package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig

class Issue241Test {

    private val json = """
        {
        	"generic": [{
        			"name": {
        				"address": "123"
        			},
        			"call_me": "ONE"
        		},
        		{
        			"name": {
        				"address_secondary": "GO TO ACCOUNT"
        			},
        			"call_me": "TWO"
        		},
        		{
        			"name": {
        				"address_present": false
        			},
        			"call_me": "THREE"
        		},
        		{
        			"name": {
        				"none": "Bill",
        				"sure": "Gates"
        			},
        			"call_me": "FOUR"
        		}
        	]
        }
    """.trimIndent()


    private val expectResult = """
        data class Output(
            val generic: List<Generic> = listOf()
        ) {
            data class Generic(
                val name: Name = Name(),
                val call_me: String = ""
            ) {
                data class Name(
                    val address: String = "",
                    val address_secondary: String = "",
                    val address_present: Boolean = false,
                    val none: String = "",
                    val sure: String = ""
                )
            }
        }
    """.trimIndent()


    @Test
    fun testIssue241() {
        TestConfig.setToTestInitState()
        TestConfig.isCommentOff = true
        TestConfig.defaultValueStrategy = DefaultValueStrategy.AvoidNull
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.isOrderByAlphabetical = false
        json.generateKotlinClassCode("Output").should.be.equal(expectResult)
    }

}