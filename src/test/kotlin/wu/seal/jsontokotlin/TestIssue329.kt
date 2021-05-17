package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class TestIssue329 {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun testIssueJson() {
        val json = """{
    "${"$"}schema":"http://json-schema.org/draft-04/schema#",
    "title":"Product",
    "description":"A product from Acme's catalog",
    "type":"object",
    "definitions":{
        "Target":{
            "oneOf":[
                {
                    "required":[
                        "A"
                    ]
                },
                {
                    "required":[
                        "B"
                    ]
                },
                {
                    "required":[
                        "C"
                    ]
                }
            ],
            "properties":{
                "A":{
                    "${"$"}ref":"#/definitions/Aa",
                    "type":"string"
                },
                "B":{
                    "${"$"}ref":"#/definitions/Bb",
                    "type":"object",
                    "additionalProperties":false
                }
            },
            "additionalProperties":false,
            "type":"object"
        },
        "Aa":{
            "type":"string"
        },
        "Bb":{
            "additionalProperties":false,
            "properties":{
                "Bba":{
                    "${"$"}ref":"#/definitions/Aa",
                    "type":"string"
                },
                "Bbb":{
                    "${"$"}ref":"#/definitions/Aa",
                    "type":"string"
                },
                "Bbc":{
                    "${"$"}ref":"#/definitions/Aa",
                    "type":"string"
                }
            },
            "required":[
                "Bba",
                "Bbb"
            ],
            "type":"object"
        }
    },
    "properties":{
        "target":{
            "${"$"}ref":"#/definitions/Target"
        }
    },
    "required":[
        "id",
        "name"
    ]
}"""
        val expected = """
            /**
             * A product from Acme's catalog
             */
            data class TestData(
                @SerializedName("target")
                val target: Target = Target()
            ) {
                data class Target(
                    @SerializedName("A")
                    val a: Aa = Aa(),
                    @SerializedName("B")
                    val b: Bb = Bb()
                ) {
                    data class Bb(
                        @SerializedName("Bba")
                        val bba: Aa = Aa(),
                        @SerializedName("Bbb")
                        val bbb: Aa = Aa(),
                        @SerializedName("Bbc")
                        val bbc: Aa = Aa()
                    )
                }
            }
        """.trimIndent()
        val s = json.generateKotlinClassCode("TestData")
        println(s)
        s.should.be.equal(expected)
    }

}