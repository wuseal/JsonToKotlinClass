package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KClassName
import java.util.*

/**
 * Type helper deal with type string
 * Created by Seal.Wu on 2017/9/21.
 */


const val TYPE_STRING = "String"
const val TYPE_INT = "Int"
const val TYPE_LONG = "Long"
const val TYPE_DOUBLE = "Double"
const val TYPE_ANY = "Any"
const val TYPE_BOOLEAN = "Boolean"

/**
 * the default type
 */
const val DEFAULT_TYPE = TYPE_ANY

fun getPrimitiveType(jsonPrimitive: JsonPrimitive): String {
    var subType = "String"
    if (jsonPrimitive.isBoolean) {
        subType = "Boolean"
    } else if (jsonPrimitive.isNumber) {
        if (jsonPrimitive.asString.contains(".")) {
            subType = "Double"
        } else if (jsonPrimitive.asLong > Integer.MAX_VALUE) {
            subType = "Long"
        } else {
            subType = "Int"
        }
    } else if (jsonPrimitive.isString) {
        subType = "String"
    }
    return subType
}


fun getJsonObjectType(type: String): String {

    return KClassName.getName(type)
}


/**
 * get the inmost child type of array type
 */
fun getChildType(arrayType: String): String {

    val tempType = arrayType.replace(Regex("<|>|List"), "")

    return tempType
}

/**
 * get the type output to the edit file
 */
fun getOutType(rawType: String): String {
    if (ConfigManager.isPropertyNullable) {
        val innerRawType = rawType.replace("?", "").replace(">", "?>")
        val outputType = innerRawType.plus("?")
        return outputType
    }
    return rawType
}

/**
 * get the type string without '?' character
 */
fun getRawType(outputType: String): String {

    return outputType.replace("?", "")
}

fun getArrayType(propertyName: String, jsonElementValue: JsonArray): String {
    val preSubType = adjustPropertyNameForGettingArrayChildType(propertyName)
    var subType = DEFAULT_TYPE
    val jsonArray = jsonElementValue

    val iterator = jsonArray.iterator()
    if (iterator.hasNext()) {
        val next = iterator.next()
        subType =
                if (next.isJsonPrimitive) {
                    getPrimitiveType(next.asJsonPrimitive)

                } else if (next.isJsonObject) {
                    getJsonObjectType(preSubType)

                } else if (next.isJsonArray) {
                    getArrayType(preSubType, next.asJsonArray)
                } else {
                    DEFAULT_TYPE
                }
    }
    return "List<$subType>"
}

fun isExpectedJsonObjArrayType(jsonElementArray: JsonArray): Boolean {
    return jsonElementArray.firstOrNull()?.isJsonObject ?: false
}

/**
 * when get the child type in an array
 * ,we need to modify the property name to make it's type name looks like a child type.
 * filter the sequence as 'list' ,"List'
 * and remove the last character 's' to make it like a child rather than a list
 */
fun adjustPropertyNameForGettingArrayChildType(property: String): String {
    var innerProperty = property
    if (innerProperty.endsWith("ies")) {
        innerProperty = innerProperty.substring(0, innerProperty.length - 3) + "y"
    } else if (innerProperty.contains("List")) {
        val firstLatterAfterListIndex = innerProperty.lastIndexOf("List") + 4
        if (innerProperty.length > firstLatterAfterListIndex) {
            val c = innerProperty[firstLatterAfterListIndex]
            if (c >= 'A' && c <= 'Z') {
                val pre = innerProperty.substring(0, innerProperty.lastIndexOf("List"))
                val end = innerProperty.substring(firstLatterAfterListIndex, innerProperty.length)
                innerProperty = pre + end
            }
        } else if (innerProperty.length == firstLatterAfterListIndex) {
            innerProperty = innerProperty.substring(0, innerProperty.lastIndexOf("List"))
        }
    } else if (innerProperty.contains("list")) {
        if (innerProperty == "list") {
            innerProperty = "Item${Date().time.toString().last()}"
        } else if (innerProperty.indexOf("list") == 0 && innerProperty.length >= 5) {
            val end = innerProperty.substring(5)
            val pre = (innerProperty[4] + "").toLowerCase()
            innerProperty = pre + end
        }
    } else if (innerProperty.endsWith("s")) {
        innerProperty = innerProperty.substring(0, innerProperty.length - 1)
    }

    return innerProperty
}
