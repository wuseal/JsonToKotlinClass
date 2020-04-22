package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.jsonschema.JsonSchema
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONSchema

class DataClassGeneratorByJSONSchemaTest {
    private val jsonSchemaJson = """
{
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
        "grades": {
          "type": "array",
          "items": {
            "type": "string"
          }
        },
        "scores": {
          "type": "array",
          "items": {
            "type": "number"
          }
        },
        "happy": {
          "type": "array",
          "items": {
            "type": "boolean"
          }
        },
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
}
"""

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun generate() {
        val jsonSchema = Gson().fromJson(jsonSchemaJson, JsonSchema::class.java)
        val dataClass = DataClassGeneratorByJSONSchema("Test", jsonSchema).generate()
        dataClass.name.should.be.equal("Test")
        dataClass.properties.size.should.be.equal(4)
        dataClass.properties[0].name.should.be.equal("id")
        dataClass.properties[0].comment.should.be.equal("The unique identifier for a product")
        dataClass.properties[0].type.should.be.equal(KotlinClass.INT.name)
        dataClass.properties[0].typeObject.should.be.equal(KotlinClass.INT)
        dataClass.properties[1].name.should.be.equal("name")
        dataClass.properties[1].comment.should.be.equal("Name of the product")
        dataClass.properties[1].type.should.be.equal(KotlinClass.STRING.name)
        dataClass.properties[1].typeObject.should.be.equal(KotlinClass.STRING)
        dataClass.properties[2].name.should.be.equal("price")
        dataClass.properties[2].type.should.be.equal(KotlinClass.DOUBLE.name)
        dataClass.properties[2].typeObject.should.be.equal(KotlinClass.DOUBLE)

        val nestProperty = dataClass.properties[3]
        nestProperty.name.should.be.equal("nested")
        nestProperty.type.should.be.equal("Nested")
        nestProperty.typeObject.should.not.be.`null`
        val nestDataClass = nestProperty.typeObject as DataClass
        nestDataClass.properties.size.should.be.equal(6)

        nestDataClass.properties[0].name.should.be.equal("grades")
        nestDataClass.properties[0].type.should.be.equal("List<String>")
        nestDataClass.properties[1].name.should.be.equal("scores")
        nestDataClass.properties[1].type.should.be.equal("List<Double>")
        nestDataClass.properties[2].name.should.be.equal("happy")
        nestDataClass.properties[2].type.should.be.equal("List<Boolean>")
        nestDataClass.properties[3].name.should.be.equal("id")
        nestDataClass.properties[3].comment.should.be.equal("The unique identifier for a product")
        nestDataClass.properties[3].type.should.be.equal(KotlinClass.INT.name)
        nestDataClass.properties[3].originJsonValue.should.be.equal("0")

        nestDataClass.properties[4].name.should.be.equal("name")
        nestDataClass.properties[4].comment.should.be.equal("Name of the product")
        nestDataClass.properties[4].type.should.be.equal(KotlinClass.STRING.name)
        nestDataClass.properties[4].originJsonValue.should.be.equal("\"\"")
        nestDataClass.properties[5].name.should.be.equal("price")
        nestDataClass.properties[5].type.should.be.equal(KotlinClass.DOUBLE.name)

    }
}