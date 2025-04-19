package wu.seal.jsontokotlin.model.jsonschema

import com.google.gson.annotations.SerializedName

// See specification: https://json-schema.org/understanding-json-schema/reference/object.html
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
    get() {
      val combinedMap = definitions.toMutableMap()
      defs?.let { combinedMap.putAll(it) }
      return combinedMap
    }
  
  //See: https://json-schema.org/understanding-json-schema/structuring.html
  fun resolveDefinition(ref: String): PropertyDef {
    if (ref.length < 2) throw IllegalArgumentException("Bad ref: $ref")
    if (!ref.startsWith("#")) throw NotImplementedError("Not local definitions are not supported (ref: $ref)")

    val path = ref.split('/')
    return when {
      path.count() == 1 -> allDefinitions.values.firstOrNull { it.id == path[0] }
          ?: throw ClassNotFoundException("Definition $ref not found")
      path[1] == "definitions" -> definitions[path[2]] 
          ?: throw ClassNotFoundException("Definition $ref not found")
      path[1] == "\$defs" -> defs?.get(path[2]) 
          ?: throw ClassNotFoundException("Definition $ref not found")
      path[1] == "properties" -> {
        var property: PropertyDef = properties?.get(path[2])
            ?: throw ClassNotFoundException("Definition $ref not found")
        val iterator = path.subList(3, path.count()).iterator()
        do {
          val next = iterator.next()
          property = when (next) {
            "properties" -> {
              val propName = iterator.next()
              property.properties?.get(propName)
                  ?: throw ClassNotFoundException("Definition $propName not found at path $ref")
            }
            "items" -> property.items ?: throw ClassNotFoundException("Definition $next not found at path $ref")
            else -> throw IllegalArgumentException("Unknown json-object property $next not found at path $ref")
          }
        } while (iterator.hasNext())

        property
      }
      else -> throw NotImplementedError("Cannot resolve ref path: $ref")
    }
  }

}

