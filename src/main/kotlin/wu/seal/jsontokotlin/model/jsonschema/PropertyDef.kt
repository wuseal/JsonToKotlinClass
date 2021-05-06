package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class PropertyDef(
        type: Any? = null,
        properties: Map<String, PropertyDef>? = null,

    //See: https://json-schema.org/understanding-json-schema/reference/array.html
        val items: PropertyDef? = null, //can be an array

    // See: https://json-schema.org/understanding-json-schema/reference/string.html#format
        val format: String? = null,

        val enum: Array<Any>? = null,
        val const: Any? = null,

    //NJsonSchema:
        @SerializedName("x-enumNames")
    val x_enumNames: Array<String>? = null,
        @SerializedName("x-enumFlags")
    val x_enumFlags: Boolean? = null

) : JsonObjectDef(type = type, properties = properties) {

  fun tryGetClassName(): String? {
    val possibleName = ref?.substringAfterLast('/')

    return if (possibleName != "items") possibleName else null
  }

}