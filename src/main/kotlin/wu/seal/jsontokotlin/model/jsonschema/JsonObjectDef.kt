package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName

open class JsonObjectDef(
    //See: https://json-schema.org/understanding-json-schema/structuring.html
        @SerializedName("\$id")
    val id: String? = null,
        @SerializedName("\$ref")
    val ref: String? = null,

        val title: String? = null,
        val description: String? = null,

        /** type may contains a string or an array of string (ArrayList),
     * where usually the first entry is "null" (property isTypeNullable)
     * and the second entry is the type string (property typeString)
     * */
    protected val type: Any? = null,

        val properties: Map<String, PropertyDef>? = null,
        val additionalProperties: Any? = null,
        val required: Array<String>? = null,

        /** See: https://json-schema.org/understanding-json-schema/reference/combining.html */
    val oneOf: Array<PropertyDef>? = null,
        val allOf: Array<PropertyDef>? = null,
        val anyOf: Array<PropertyDef>? = null,
        val not: Array<PropertyDef>? = null,

        @SerializedName("x-abstract")
    val x_abstract: Boolean? = null

) {

    /** returns correct JsonSchema type as string */
    val typeString: String?
        get() = if (type is ArrayList<*>) type.first { it != "null" } as String else type as? String

    /** returns true if the object can be null */
    val isTypeNullable: Boolean
        get() = when {
            type is ArrayList<*> -> type.any { it == "null" }
            oneOf?.any { it.type == "null" } == true -> true
            else -> typeString == "null"
        }

}
