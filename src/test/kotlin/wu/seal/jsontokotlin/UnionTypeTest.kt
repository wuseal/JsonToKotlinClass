package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class UnionTypeTest {
    @Before
    fun setUp() {
        TestConfig.setToTestInitStateForJsonSchema()
    }

    @Test
    fun testUnionType() {
        // TODO: Top-level union types don't work yet
        val json = """
            {
  "${"$"}ref": "#/definitions/Container",
  "${"$"}schema": "http://json-schema.org/draft-07/schema#",
  "definitions": {
    "A": {
      "additionalProperties": false,
      "properties": {
        "baseMember": {
          "type": "string"
        },
        "childMember": {
          "type": "number"
        },
        "type": {
          "const": 0,
          "type": "number"
        }
      },
      "required": [
        "baseMember",
        "childMember",
        "type"
      ],
      "type": "object"
    },
    "B": {
      "additionalProperties": false,
      "properties": {
        "baseMember": {
          "type": "string"
        },
        "childMember": {
          "type": "string"
        },
        "type": {
          "const": 1,
          "type": "number"
        }
      },
      "required": [
        "baseMember",
        "childMember",
        "type"
      ],
      "type": "object"
    },
    "Container": {
      "additionalProperties": false,
      "properties": {
        "union": {
          "${"$"}ref": "#/definitions/Union"
        }
      },
      "required": [
        "union"
      ],
      "type": "object"
    },
    "Union": {
      "anyOf": [
        {
          "${"$"}ref": "#/definitions/A"
        },
        {
          "${"$"}ref": "#/definitions/B"
        }
      ]
    }
  }
}
        """.trimIndent()

        val expected = """
data class Test(
    val union: Union
) {
    sealed class Union(val type: Double) {
        data class A(
            val baseMember: String,
            val childMember: Double,
        ) : Union(type = 0.0)
        data class B(
            val baseMember: String,
            val childMember: String,
        ) : Union(type = 1.0)
    }


}            
        """.trim()

        val result = json.generateKotlinClass("Test").getCode()
        result.trim().should.be.equal(expected)
    }
}
