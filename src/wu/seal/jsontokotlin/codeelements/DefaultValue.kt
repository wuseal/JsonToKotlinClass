package wu.seal.jsontokotlin.codeelements

import wu.seal.jsontokotlin.utils.*

/**
 * Default Value relative
 * Created by Seal.wu on 2017/9/25.
 */

fun getDefaultValue(propertyType: String): String {

    val rawType = getRawType(propertyType)

    if (rawType == TYPE_INT) {
        return 0.toString()
    } else if (rawType == TYPE_LONG) {
        return 0L.toString()
    } else if (rawType == TYPE_STRING) {
        return "\"\""
    } else if (rawType == TYPE_DOUBLE) {
        return 0.0.toString()
    } else if (rawType == TYPE_BOOLEAN) {
        return false.toString()
    } else if (rawType.contains("List")) {
        return "listOf()"
    } else if (rawType == TYPE_ANY) {
        return "Any()"
    } else {
        return "$rawType()"
    }
}
