package wu.seal.jsontokotlin.bean.jsonschema

import com.google.gson.annotations.SerializedName

typealias JsonSchemaProperty = String

class JsonSchema(
        @SerializedName("\$schema")
        val schema: String?,
        val title: String?,
        val description: String?,
        val type: String?,
        val properties: Map<JsonSchemaProperty, PropertyDef>,
        val required: Array<JsonSchemaProperty>
)
