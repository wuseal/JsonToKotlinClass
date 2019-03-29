package wu.seal.jsontokotlin.bean.jsonschema

import com.google.gson.annotations.SerializedName

class JsonSchema(
  @SerializedName("\$schema")
  val schema: String?,
  title: String = "",
  description: String = "",
  type: String = "",
  properties: Map<String, PropertyDef>,
  required: Array<String>)
  : JsonObjectDef(title, description, type, properties, required)
