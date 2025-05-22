package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName

/**
 * Represents a JSON schema document.
 *
 * This class models the structure of a JSON schema, allowing for parsing and resolving
 * references (`$ref`) within the schema. It supports standard schema keywords like
 * `definitions`, `$defs`, and `properties`.
 *
 * See JSON Schema Specification: [https://json-schema.org/understanding-json-schema/](https://json-schema.org/understanding-json-schema/)
 *
 * @property schema The value of the `\$schema` keyword, indicating the schema dialect.
 * @property definitions A map of definitions, primarily used in older JSON schema versions.
 * @property defs A map of definitions, introduced in Draft 2019-09 as a replacement for `definitions`.
 */
class JsonSchema(
    @SerializedName("\$schema")
    val schema: String? = null,
    val definitions: Map<String, PropertyDef> = emptyMap(),
    @SerializedName("\$defs")
    val defs: Map<String, PropertyDef>? = null
) : JsonObjectDef() {

  // Get a combined map of both definitions and $defs
  // This allows backward compatibility with older schema versions
  private val allDefinitions: Map<String, PropertyDef>
    get() = definitions + (defs ?: emptyMap())

  /**
   * Resolves a JSON Pointer reference (`$ref`) to a [PropertyDef] within this schema.
   *
   * Currently, only local references (starting with `#`) are supported.
   * Examples of supported $ref formats:
   * - `#/definitions/MyType`
   * - `#/\$defs/AnotherType`
   * - `#/properties/user/properties/address`
   * - `#MyObject` (if `MyObject` is an id of a definition at the root)
   *
   * See JSON Schema structuring: [https://json-schema.org/understanding-json-schema/structuring.html](https://json-schema.org/understanding-json-schema/structuring.html)
   *
   * @param ref The JSON Pointer reference string (e.g., `#/definitions/User`).
   * @return The resolved [PropertyDef].
   * @throws IllegalArgumentException if the `ref` string is malformed (e.g., too short).
   * @throws NotImplementedError if the `ref` points to a non-local definition (doesn't start with '#').
   * @throws ClassNotFoundException if the definition pointed to by `ref` cannot be found.
   */
  fun resolveDefinition(ref: String): PropertyDef {
    if (ref.length < 2) throw IllegalArgumentException("Bad ref: $ref")
    if (!ref.startsWith("#")) throw NotImplementedError("Not local definitions are not supported (ref: $ref)")

    return resolveLocalDefinition(ref)
  }

  /**
   * Resolves a local definition path (starting with '#').
   * @param ref The definition reference string.
   * @return The resolved [PropertyDef].
   * @throws ClassNotFoundException if the definition is not found.
   * @throws NotImplementedError if the path structure is not supported.
   * @throws IllegalArgumentException if the path contains unknown components.
   */
  private fun resolveLocalDefinition(ref: String): PropertyDef {
    val path = ref.split('/')
    return when {
      path.count() == 1 -> allDefinitions.values.firstOrNull { it.id == path[0] } // TODO: This could be path[0].substring(1) if # is always present
          ?: throw ClassNotFoundException("Definition $ref not found")
      path[1] == "definitions" -> findDefinitionInMaps(path[2], definitions, ref, "definitions")
      path[1] == "\$defs" -> findDefinitionInMaps(path[2], defs, ref, "\$defs")
      path[1] == "properties" -> {
        val initialProperty = properties?.get(path[2])
            ?: throw ClassNotFoundException("Definition '${path[2]}' not found in properties at path $ref")
        findPropertyRecursive(initialProperty, path.subList(3, path.count()).iterator(), ref)
      }
      else -> throw NotImplementedError("Cannot resolve ref path: $ref")
    }
  }

  /**
   * Finds a definition in the provided map.
   * @param defName The name of the definition to find.
   * @param map The map to search in (can be nullable, e.g. `defs`).
   * @param originalRef The original reference string (for error reporting).
   * @param mapName The name of the map being searched (for error reporting).
   * @return The resolved [PropertyDef].
   * @throws ClassNotFoundException if the definition is not found in the map.
   */
  private fun findDefinitionInMaps(
      defName: String,
      map: Map<String, PropertyDef>?,
      originalRef: String,
      mapName: String
  ): PropertyDef {
    return map?.get(defName)
        ?: throw ClassNotFoundException("Definition '$defName' not found in '$mapName' at path $originalRef")
  }

  /**
   * Recursively traverses properties based on the path segments.
   * @param currentProperty The current [PropertyDef] being inspected.
   * @param pathIterator An iterator for the remaining path segments.
   * @param originalRef The original reference string (for error reporting).
   * @return The resolved [PropertyDef].
   * @throws ClassNotFoundException if a segment in the path is not found.
   * @throws IllegalArgumentException if an unknown path segment is encountered.
   */
  private fun findPropertyRecursive(
      currentProperty: PropertyDef,
      pathIterator: Iterator<String>,
      originalRef: String
  ): PropertyDef {
    var property = currentProperty
    while (pathIterator.hasNext()) {
      val segment = pathIterator.next()
      property = when (segment) {
        "properties" -> {
          if (!pathIterator.hasNext()) throw IllegalArgumentException("Missing property name after 'properties' in $originalRef")
          val propName = pathIterator.next()
          property.properties?.get(propName)
            ?: throw ClassNotFoundException("Property '$propName' not found under '${property.id ?: "unknown"}' at path $originalRef")
        }
        "items" -> property.items
            ?: throw ClassNotFoundException("Property 'items' not found under '${property.id ?: "unknown"}' at path $originalRef")
        else -> throw IllegalArgumentException("Unknown json-object property '$segment' in path $originalRef")
      }
    }
    return property
  }
}

