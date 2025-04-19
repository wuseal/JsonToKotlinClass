package wu.seal.jsontokotlin.utils

import com.google.gson.*
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import java.lang.StringBuilder
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


/**
 * Return the kotlin type of a JsonArray with all elements are numbers
 */
fun getKotlinNumberClass(jsonArray: JsonArray): KotlinClass {
    var ans: KotlinClass = KotlinClass.INT
    jsonArray.forEach {
        if (it.isJsonPrimitive.not() || it.asJsonPrimitive.isNumber.not()) {
            throw IllegalArgumentException("the argument should be a json array with all elements are number type")
        }
        val kotlinType = it.asJsonPrimitive.toKotlinClass();
        if (kotlinType.getNumLevel() > ans.getNumLevel()) {
            ans = kotlinType
        }
    }
    return ans
}


/**
 * Return the level of value scope for kotlin number type
 */
fun KotlinClass.getNumLevel(): Int {
    return when {
        this == KotlinClass.INT -> 0;
        this == KotlinClass.LONG -> 1;
        this == KotlinClass.DOUBLE -> 2;
        else -> -1
    }
}

fun JsonPrimitive.toKotlinClass(): KotlinClass {
    return when {
        isBoolean -> KotlinClass.BOOLEAN
        isNumber -> when {
            asString.contains(".") -> KotlinClass.DOUBLE
            asLong > Integer.MAX_VALUE || asLong < Integer.MIN_VALUE -> KotlinClass.LONG
            else -> KotlinClass.INT
        }
        isString -> KotlinClass.STRING
        else -> KotlinClass.STRING
    }
}

/**
 * convert string into annotation comments format,TODO need automatic line wrapping
 */
fun String.toAnnotationComments(indent: String = ""): String {
    return if (this.isBlank()) "" else {
        StringBuffer().append("$indent/**\n")
            .append("$indent * $this\n")
            .append("$indent */\n")
            .toString()
    }
}

fun String.toJavaDocMultilineComment(): String {
    return if (this.isBlank()) {
        ""
    } else {
        "/**\n" +
            "$this\n" +
            "*/\n"
    }
}

fun String.addIndent(indent: String): String =
    this.lines().joinToString("\n") { if (it.isBlank()) it else "$indent$it" }

operator fun String.times(count: Int): String {
    if (count < 1) return ""
    var i = 0
    val string = this
    val sb = StringBuilder().apply {
        while (i < count) {
            append(string)
            i++
        }
    }
    return sb.toString()
}

fun StringBuilder.newLine(): StringBuilder {
    this.append("\n")
    return this
}

fun <T> KotlinClass?.runWhenDataClass(block: DataClass.() -> T) = (this as? DataClass)?.run(block)

/**
 *
 * If class name the same with XXXX append or only diff with number, then treat them as the same class to do distinct
 * then distinct them by its properties code
 */
private fun List<KotlinClass>.distinctByPropertiesAndSimilarClassNameOneTime(): List<KotlinClass> {
    fun KotlinClass.codeWithoutXAndNumberClassName(): String {
        return getOnlyCurrentCode().replaceFirst(name, name.replace("X", "").replace("\\d".toRegex(), ""))
    }

    fun KotlinClass.replaceClassRefRecursive(
        replaceRule: (curRef: KotlinClass) -> KotlinClass
    ): KotlinClass {
        val rule = referencedClasses.filter { it.modifiable }.associateWith {
            replaceRule(it.replaceClassRefRecursive(replaceRule))
        }
        return replaceReferencedClasses(rule)
    }
    //If class name the same with XXXX append or only diff with number, then group them in the same entry
    //obtain the class to replace other ref class, if the replace times is less then 2, then it means that no need to replace
    val targetClass = groupBy { it.codeWithoutXAndNumberClassName() }.maxByOrNull { it.value.size }
        ?.takeIf { it.value.size > 1 }?.value?.first() ?: return this

    return mapNotNull {
        if (targetClass == it) return@mapNotNull it
        if (it.codeWithoutXAndNumberClassName() == targetClass.codeWithoutXAndNumberClassName()) {
            return@mapNotNull null // removing the classes that we are replacing
        }
        it.replaceClassRefRecursive {
            if (it.codeWithoutXAndNumberClassName() == targetClass.codeWithoutXAndNumberClassName()) {
                targetClass
            } else it
        }
    }
}

fun List<KotlinClass>.distinctByPropertiesAndSimilarClassName(): List<KotlinClass> {
    var lastSize = Int.MAX_VALUE
    var currentSize = size
    var currentResult: List<KotlinClass> = distinctByPropertiesAndSimilarClassNameOneTime()
    do {
        currentResult = currentResult.distinctByPropertiesAndSimilarClassNameOneTime()
        lastSize = currentSize
        currentSize = currentResult.size
    } while (currentSize != lastSize)
    return currentResult
}


fun String.isJSONSchema(): Boolean {
    val jsonElement = Gson().fromJson(this, JsonElement::class.java)
    return if (jsonElement.isJsonObject) {
        with(jsonElement.asJsonObject) {
            has("\$schema") || has("\$defs") || has("definitions")
        }
    } else {
        false
    }
}

/**
 * get a Fat JsonObject whose fields contain all the objects' fields around the objects of the json array
 */
fun JsonArray.getFatJsonObject(): JsonObject {
    if (size() == 0 || !allItemAreObjectElement()) {
        throw IllegalStateException("input arg jsonArray must not be empty and all element should be json object! ")
    }
    val allFields = flatMap { it.asJsonObject.entrySet().map { entry -> Pair(entry.key, entry.value) } }
    val fatJsonObject = JsonObject().apply {
        fun JsonElement.isEmptyJsonObj(): Boolean = isJsonObject && asJsonObject.entrySet().isEmpty()
        fun JsonElement.isEmptyJsonArray(): Boolean = isJsonArray && asJsonArray.size() == 0
        fun JsonElement.numberLevel(): Int {
            return if (isJsonPrimitive) {
                asJsonPrimitive.toKotlinClass().getNumLevel()
            } else -1
        }
        //create good fatJsonObject contains all fields, and try not use null or empty value
        allFields.forEach { (key, value) ->
            if (has(key).not()) {
                add(key, value)
            } else {
                //when the the field is a number type, we need to select the value with largest scope
                //e.g.  given [{"key":10},{"key":11.2}]
                //we should use the object with value = 11.2 to represent the object type which will be Double
                val newIsHigherNumber = value.numberLevel() > this[key].numberLevel()
                val newIsNotNullOrEmpty = value !is JsonNull && !value.isEmptyJsonObj() && !value.isEmptyJsonArray()
                val oldIsNullOrEmpty =
                    (this[key] is JsonNull) or this[key].isEmptyJsonObj() or this[key].isEmptyJsonArray()
                val needReplaceValue = newIsHigherNumber || (newIsNotNullOrEmpty and oldIsNullOrEmpty)
                if (needReplaceValue) {
                    add(key, value)
                }
            }
        }
        // add nullable type fields
        forEach {
            val item = it.asJsonObject
            item.entrySet().forEach { (key, value) ->
                //if the value is null or empty json obj or empty json array,
                // then translate it to a new special property to indicate that the property is nullable
                //later will consume this property (do it here[DataClassGeneratorByJSONObject#consumeBackstageProperties])
                // delete it or translate it back to normal property without [BACKSTAGE_NULLABLE_POSTFIX] when consume it
                // and will not be generated in final code
                if (value is JsonNull || value.isEmptyJsonObj()) {
                    add(key + BACKSTAGE_NULLABLE_POSTFIX, value)
                }
            }
            //if some json fields in fat json object not inside one item, then we treat them as nullable
            entrySet().map { it.key.replace(BACKSTAGE_NULLABLE_POSTFIX, "") }
                .filter { it !in item.entrySet().map { it.key.replace(BACKSTAGE_NULLABLE_POSTFIX, "") } }.forEach {
                    add(it + BACKSTAGE_NULLABLE_POSTFIX, null)
                }
        }

        //let all fields value to be fatJsonObject or fat JsonArray
        allFields.filter { it.second !is JsonNull }.groupBy { it.first }.forEach {
            val jsonArrayObj = JsonArray().apply {
                it.value.map { it.second }.forEach {
                    this.add(it)
                }
            }
            if (jsonArrayObj.allItemAreObjectElement()) {
                this.add(it.key, jsonArrayObj.getFatJsonObject())
            } else if (jsonArrayObj.allItemAreArrayElement()) {
                this.add(it.key, jsonArrayObj.getFatJsonArray())
            }
        }
    }
    return fatJsonObject
}

fun JsonArray.getFatJsonArray(): JsonArray {
    if (size() == 0 || !allItemAreArrayElement()) {
        throw IllegalStateException("input arg jsonArray must not be empty and all element should be json array! ")
    }
    val fatJsonArray = JsonArray()
    forEach {
        fatJsonArray.addAll(it.asJsonArray)
    }
    return fatJsonArray
}