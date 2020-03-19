package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import java.util.regex.Pattern

/**
 * How many substring in the parent string
 */
fun String.numberOf(subString: String): Int {
    var count = 0
    val pattern = Pattern.compile(subString)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        count++
    }
    return count
}

/**
 * array only has one element
 */
private fun JsonArray.onlyHasOneElement(): Boolean {
    return size() == 1
}

/**
 * array only has object element
 */
fun JsonArray.allItemAreNullElement(): Boolean {
    forEach {
        if (it.isJsonNull.not()) {
            return false
        }
    }
    return true
}

/**
 * array only has object element
 */
fun JsonArray.allItemAreObjectElement(): Boolean {
    forEach {
        if (it.isJsonObject.not()) {
            return false
        }
    }
    return true
}

/**
 * array only has array element
 */
fun JsonArray.allItemAreArrayElement(): Boolean {
    forEach {
        if (it.isJsonArray.not()) {
            return false
        }
    }
    return true
}

/**
 * if Multidimensional Arrays only has one element
 */
tailrec fun JsonArray.onlyHasOneElementRecursive(): Boolean {

    if (size() == 0) {
        return false
    }
    if (onlyHasOneElement().not()) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonObject || get(0).isJsonNull) {
        return true
    }

    return get(0).asJsonArray.onlyHasOneElementRecursive()
}


/**
 * if Multidimensional Arrays only has one element
 */
tailrec fun JsonArray.onlyHasOneObjectElementRecursive(): Boolean {

    if (size() == 0) {
        return false
    }
    if (onlyHasOneElement().not()) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonNull) {
        return false
    }

    if (get(0).isJsonObject) {
        return true
    }
    return get(0).asJsonArray.onlyHasOneObjectElementRecursive()
}


/**
 * if Multidimensional Arrays only has one dimension contains element and the elements  all are object element
 */
tailrec fun JsonArray.onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive(): Boolean {
    if (size() == 0) {
        return false
    }

    if (onlyHasOneElement().not()) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonNull) {
        return false
    }

    if (get(0).isJsonArray && get(0).asJsonArray.allItemAreObjectElement()) {
        return true
    }

    return get(0).asJsonArray.onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive()
}

fun JsonArray.allChildrenAreEmptyArray(): Boolean {

    if (size() == 0) {
        return true
    }

    return all { (it as? JsonArray)?.allChildrenAreEmptyArray() ?: false }
}


/**
 * filter out all null json element of JsonArray
 */
fun JsonArray.filterOutNullElement(): JsonArray {

    val jsonElements = filter { it.isJsonNull.not() }
    return JsonArray().apply {
        jsonElements.forEach { jsonElement ->
            add(jsonElement)
        }
    }

}

/**
 * Return true if this string contains any sequence of characters of the list
 */
fun String.containsAnyOf(list: List<CharSequence>) = list.any { this.contains(it) }

fun JsonArray.allElementAreSamePrimitiveType(): Boolean {
    var allElementAreSamePrimitiveType = true
    forEach {
        if (it.isJsonPrimitive.not()) {
            allElementAreSamePrimitiveType = false
            return allElementAreSamePrimitiveType
        }
        if (theSamePrimitiveType(this[0].asJsonPrimitive, it.asJsonPrimitive).not()) {
            allElementAreSamePrimitiveType = false
            return allElementAreSamePrimitiveType
        }
    }
    return allElementAreSamePrimitiveType
}

fun theSamePrimitiveType(first: JsonPrimitive, second: JsonPrimitive): Boolean {

    val sameBoolean = first.isBoolean && second.isBoolean

    val sameNumber = first.isNumber && second.isNumber

    val sameString = first.isString && second.isString

    return sameBoolean || sameNumber || sameString
}

fun JsonPrimitive.toKotlinClass(): KotlinClass {
    return when {
        isBoolean -> KotlinClass.BOOLEAN
        isNumber -> when {
            asString.contains(".") -> KotlinClass.DOUBLE
            asLong > Integer.MAX_VALUE -> KotlinClass.LONG
            else -> KotlinClass.INT
        }
        isString -> KotlinClass.STRING
        else -> KotlinClass.STRING
    }
}

/**
 * convert string into annotation comments format,TODO need automatic line wrapping
 */
fun String.toAnnotationComments() = this.toAnnotationComments("")

fun String.toAnnotationComments(indent: String): String {
    return if (this.isBlank()) "" else {
        StringBuffer().append("$indent/**\n")
                .append("$indent * $this\n")
                .append("$indent */\n")
                .toString()
    }
}

fun String.addIndent(indent: String): String = this.lines().joinToString("\n") { if (it.isBlank()) it else "$indent$it" }