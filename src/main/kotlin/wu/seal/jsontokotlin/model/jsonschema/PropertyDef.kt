package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName

/**
 * Represents a property definition within a JSON Schema.
 *
 * This class extends [JsonObjectDef] and includes additional properties specific to
 * schema definitions that can act as properties of an object, or definitions for items in an array.
 * It includes keywords like `items` (for arrays), `format` (for strings), `enum`, and `const`.
 *
 * @param type The raw type value from JSON, inherited from [JsonObjectDef]. Can be a String or List of Strings.
 * @param properties The nested properties if this schema defines an object, inherited from [JsonObjectDef].
 * @property items Defines the schema for items in an array. Applicable when the `type` is "array".
 *               See: [https://json-schema.org/understanding-json-schema/reference/array.html#items](https://json-schema.org/understanding-json-schema/reference/array.html#items)
 * @property format Provides semantic meaning to string-based types (e.g., "date-time", "email", "uuid").
 *                See: [https://json-schema.org/understanding-json-schema/reference/string.html#format](https://json-schema.org/understanding-json-schema/reference/string.html#format)
 * @property enum Specifies an array of exact valid values for this property.
 *              See: [https://json-schema.org/understanding-json-schema/reference/generic.html#enumerated-values](https://json-schema.org/understanding-json-schema/reference/generic.html#enumerated-values)
 * @property const Specifies the exact value that this property must have.
 *               See: [https://json-schema.org/understanding-json-schema/reference/generic.html#constant-values](https://json-schema.org/understanding-json-schema/reference/generic.html#constant-values)
 * @property x_enumNames Custom NJsonSchema extension: Provides human-readable names for `enum` values.
 * @property x_enumFlags Custom NJsonSchema extension: If true, suggests the enum can be treated as a bit flags enum.
 */
class PropertyDef(
    type: Any? = null, /* String or List<String> */
    properties: Map<String, PropertyDef>? = null,

    @SerializedName("items")
    val items: PropertyDef? = null,

    @SerializedName("format")
    val format: String? = null,

    @SerializedName("enum")
    val enum: Array<Any>? = null,

    @SerializedName("const")
    val const: Any? = null,

    // NJsonSchema specific extensions
    @SerializedName("x-enumNames")
    val x_enumNames: Array<String>? = null,

    @SerializedName("x-enumFlags")
    val x_enumFlags: Boolean? = null

) : JsonObjectDef(
    type = type,
    properties = properties
    // id, ref, title, description, additionalProperties, required, oneOf, allOf, anyOf, not, x_abstract
    // are all available from JsonObjectDef and can be passed via constructor if needed,
    // but for typical PropertyDef usage within 'properties' or 'items', they are less common directly.
) {

    /**
     * Attempts to extract a potential class name from the `\$ref` property.
     *
     * This method assumes that the `$ref` value is a local reference (e.g., `#/definitions/MyClass`)
     * and extracts the last path segment as the potential class name.
     * It specifically avoids returning "items" as a class name, as "items" is a keyword
     * in JSON Schema referring to the schema of array elements, not typically a class name itself.
     *
     * @return The extracted string after the last '/' in the `ref` property, or `null` if `ref` is null,
     *         empty, or the extracted name is "items".
     */
    fun tryGetClassName(): String? {
        val possibleName = ref?.substringAfterLast('/')
        // Avoid using "items" as a class name, as it's a schema keyword.
        return if (possibleName != null && possibleName.isNotEmpty() && possibleName != "items") possibleName else null
    }
}