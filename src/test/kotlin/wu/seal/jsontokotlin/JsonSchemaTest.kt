package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class JsonSchemaTest {

  @Before
  fun before() {
    TestConfig.setToTestInitState()
  }

  @Test
  fun testBasicJsonSchema() {
    val json = """{
  "${"$"}schema": "http://json-schema.org/draft-04/schema#",
  "title": "Product",
  "description": "A product from Acme\u0027s catalog",
  "type": "object",
  "properties": {
    "id": {
      "description": "The unique identifier for a product",
      "type": "integer"
    },
    "name": {
      "description": "Name of the product",
      "type": "string"
    },
    "price": {
      "type": "number",
      "minimum": 0,
      "exclusiveMinimum": true
    },
    "nested": {
      "type": "object",
      "properties": {
        "id": {
          "description": "The unique identifier for a product",
          "type": "integer"
        },
        "name": {
          "description": "Name of the product",
          "type": "string"
        },
        "price": {
          "type": "number",
          "minimum": 0,
          "exclusiveMinimum": true
        }
      },
      "required": ["id", "name"]
    }
  },
  "required": [
    "id",
    "name",
    "price"
  ]
}""".trimIndent()
    val s = json.generateKotlinClassCode("TestData")
    println(s)
  }
}
