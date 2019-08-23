package wu.seal.jsontokotlin.bean.jsonschema

import com.google.gson.annotations.SerializedName

class JsonSchema(
  @SerializedName("\$schema")
  val schema: String? = null,
  title: String = "",
  description: String = "",
  type: String? = "",
  properties: Map<String, JsonSchema> = mapOf(),
  required: Array<String> = emptyArray())
  : ObjectPropertyDef(description, type, properties, required = required)
