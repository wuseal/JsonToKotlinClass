package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName

/**
 * Represents a base JSON object definition in a JSON Schema.
 * This class includes common properties found in JSON Schema objects, such as `id`, `\$ref`,
 * `title`, `description`, `type`, and validation keywords like `properties`, `oneOf`, etc.
 *
 * See JSON Schema Specification: [https://json-schema.org/understanding-json-schema/](https://json-schema.org/understanding-json-schema/)
 */
open class JsonObjectDef(
    /**
     * The `\$id` keyword defines a URI for the schema, and the base URI that other URI references within the schema are resolved against.
     * See: [https://json-schema.org/understanding-json-schema/structuring.html#the-id-property](https://json-schema.org/understanding-json-schema/structuring.html#the-id-property)
     */
    @SerializedName("\$id")
    val id: String? = null,

    /**
     * The `\$ref` keyword is used to reference another schema.
     * This allows for reusing parts of schemas or creating complex recursive schemas.
     * See: [https://json-schema.org/understanding-json-schema/structuring.html#ref](https://json-schema.org/understanding-json-schema/structuring.html#ref)
     */
    @SerializedName("\$ref")
    val ref: String? = null,

    /**
     * The `title` keyword provides a short, human-readable summary of the schema's purpose.
     */
    val title: String? = null,

    /**
     * The `description` keyword provides a more detailed explanation of the schema's purpose.
     */
    val description: String? = null,

    /**
     * The `type` keyword defines the data type for a schema.
     * It can be a string (e.g., "object", "string", "number") or a list of strings (e.g., ["string", "null"]).
     * This property stores the raw value from JSON, which can be a String or a List.
     * Use [typeString] to get the actual type name and [isTypeNullable] to check for nullability.
     * See: [https://json-schema.org/understanding-json-schema/reference/type.html](https://json-schema.org/understanding-json-schema/reference/type.html)
     */
    protected val type: Any? = null, /* String or List<String> */

    /**
     * The `properties` keyword defines a map of property names to their schema definitions for an object type.
     * See: [https://json-schema.org/understanding-json-schema/reference/object.html#properties](https://json-schema.org/understanding-json-schema/reference/object.html#properties)
     */
    val properties: Map<String, PropertyDef>? = null,

    /**
     * The `additionalProperties` keyword controls whether additional properties are allowed in an object,
     * and can also define a schema for those additional properties.
     * It can be a boolean (true/false) or a schema object.
     * See: [https://json-schema.org/understanding-json-schema/reference/object.html#additionalproperties](https://json-schema.org/understanding-json-schema/reference/object.html#additionalproperties)
     */
    val additionalProperties: Any? = null, // Boolean or PropertyDef

    /**
     * The `required` keyword specifies an array of property names that must be present in an object.
     * See: [https://json-schema.org/understanding-json-schema/reference/object.html#required](https://json-schema.org/understanding-json-schema/reference/object.html#required)
     */
    val required: Array<String>? = null,

    /**
     * The `oneOf` keyword specifies that an instance must be valid against exactly one of the subschemas in the array.
     * See: [https://json-schema.org/understanding-json-schema/reference/combining.html#oneof](https://json-schema.org/understanding-json-schema/reference/combining.html#oneof)
     */
    val oneOf: Array<PropertyDef>? = null,

    /**
     * The `allOf` keyword specifies that an instance must be valid against all of the subschemas in the array.
     * See: [https://json-schema.org/understanding-json-schema/reference/combining.html#allof](https://json-schema.org/understanding-json-schema/reference/combining.html#allof)
     */
    val allOf: Array<PropertyDef>? = null,

    /**
     * The `anyOf` keyword specifies that an instance must be valid against at least one of the subschemas in the array.
     * See: [https://json-schema.org/understanding-json-schema/reference/combining.html#anyof](https://json-schema.org/understanding-json-schema/reference/combining.html#anyof)
     */
    val anyOf: Array<PropertyDef>? = null,

    /**
     * The `not` keyword specifies that an instance must not be valid against the given subschema.
     * See: [https://json-schema.org/understanding-json-schema/reference/combining.html#not](https://json-schema.org/understanding-json-schema/reference/combining.html#not)
     */
    val not: Array<PropertyDef>? = null,

    /**
     * Custom extension property `x-abstract`. If true, suggests this schema definition is intended
     * as an abstract base and might not be instantiated directly.
     */
    @SerializedName("x-abstract")
    val x_abstract: Boolean? = null

) {

    /**
     * Gets the primary JSON Schema type as a string.
     * If the `type` property is an array (e.g., `["null", "string"]`), this returns the first non-"null" type.
     * If `type` is a single string, it returns that string.
     * Returns `null` if the type cannot be determined or is explicitly "null" without other types.
     */
    val typeString: String?
        get() = if (type is ArrayList<*>) type.firstOrNull { it != "null" } as? String else type as? String

    /**
     * Checks if the schema definition allows for a "null" type.
     * This can be true if:
     * - The `type` property is an array containing "null" (or actual `null`).
     * - Any subschema under `oneOf` allows for a "null" type.
     * - The [typeString] itself is "null" or `null`.
     */
    val isTypeNullable: Boolean
        get() = when {
            type is ArrayList<*> -> type.any { it == null || it == "null" }
            oneOf?.any { it.type == null || (it.type as? String) == "null" || (it.type is ArrayList<*> && it.type.any { subType -> subType == null || subType == "null"}) } == true -> true
            else -> typeString == null || typeString == "null"
        }

}
