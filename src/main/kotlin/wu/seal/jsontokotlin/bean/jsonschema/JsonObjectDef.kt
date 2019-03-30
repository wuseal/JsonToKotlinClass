package wu.seal.jsontokotlin.bean.jsonschema

open class JsonObjectDef(
  val title: String = "",
  val description: String? = "",
  val type: String = "string",
  val properties: Map<String, PropertyDef> = emptyMap(),
  val required: Array<String> = emptyArray()
)
