package wu.seal.jsontokotlin

import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive

/**
 * Type helper deal with type string
 * Created by Seal.Wu on 2017/9/21.
 */


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


fun getArrayType(propertyName: String, jsonElementValue: JsonArray): String {
    var innerPropertyName = propertyName
    var type = "List<String>"
    val jsonArray = jsonElementValue

    val iterator = jsonArray.iterator()
    if (iterator.hasNext()) {
        val next = iterator.next()
        val subType =
                if (next.isJsonPrimitive) {
                    getPrimitiveType(next.asJsonPrimitive)

                } else if (next.isJsonObject) {
                    getJsonObjectType(innerPropertyName)

                } else if (next.isJsonArray) {
                    innerPropertyName = modifyPropertyForArrayObjType(innerPropertyName)
                    getArrayType(innerPropertyName, next as JsonArray)

                } else if (next.isJsonNull) {
                    "Any"

                } else {
                    "String"
                }

        type = "List<$subType>"
    } else {
        type = "List<Any>"

    }
    return type
}

fun isExpectedJsonObjArrayType(jsonElementArray: JsonArray): Boolean {
    return jsonElementArray.firstOrNull()?.isJsonObject ?: false
}

private fun modifyPropertyForArrayObjType(property: String): String {
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
        if (innerProperty.indexOf("list") == 0) {
            val end = innerProperty.substring(5)
            val pre = (innerProperty[4] + "").toLowerCase()
            innerProperty = pre + end
        }
    } else if (innerProperty.endsWith("s")) {
        innerProperty = innerProperty.substring(0, innerProperty.length - 1)
    }

    return innerProperty
}