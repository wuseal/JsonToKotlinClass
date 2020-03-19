package wu.seal.jsontokotlin.model.codeelements

import wu.seal.jsontokotlin.utils.*

/**
 * Default Value relative
 * Created by Seal.wu on 2017/9/25.
 */

fun getDefaultValue(propertyType: String): String {

    val rawType = getRawType(propertyType)

    return when {
        rawType == TYPE_INT -> 0.toString()
        rawType == TYPE_LONG -> 0L.toString()
        rawType == TYPE_STRING -> "\"\""
        rawType == TYPE_DOUBLE -> 0.0.toString()
        rawType == TYPE_BOOLEAN -> false.toString()
        rawType.contains("List<") -> "listOf()"
        rawType.contains("Map<") -> "mapOf()"
        rawType == TYPE_ANY -> "Any()"
        rawType.contains("Array<") -> "emptyArray()"
        else -> "$rawType()"
    }
}


fun getDefaultValueAllowNull(propertyType: String) =
    if (propertyType.endsWith("?")) "null" else getDefaultValue(propertyType)

