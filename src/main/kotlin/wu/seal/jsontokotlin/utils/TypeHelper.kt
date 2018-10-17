package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.PropertyTypeStrategy
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

const val MAP_DEFAULT_OBJECT_VALUE_TYPE = "MapValue"
const val MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE = "Item"

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

    val tempType = arrayType.replace(Regex("List<|>"), "")

    return tempType
}

/**
 * get the type output to the edit file
 */
fun getOutType(rawType: String, value: Any?): String {
    if (ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable) {
        val innerRawType = rawType.replace("?", "").replace(">", "?>")
        val outputType = innerRawType.plus("?")
        return outputType
    } else if (ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot && value == null) {
        return rawType.plus("?")
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
                    if (jsonArray.size() == 1) {
                        getArrayType(preSubType, next.asJsonArray)
                    } else {
                        DEFAULT_TYPE
                    }
                } else {
                    DEFAULT_TYPE
                }
        return "List<$subType>"
    }
    return "List<$subType>"
}

fun isExpectedJsonObjArrayType(jsonElementArray: JsonArray): Boolean {
    return jsonElementArray.firstOrNull()?.isJsonObject ?: false
}

/**
 * when get the child type in an array
 * ,we need to make the property name be legal and then modify the property name to make it's type name looks like a child type.
 * filter the sequence as 'list' ,"List'
 * and remove the last character 's' to make it like a child rather than a list
 */
fun adjustPropertyNameForGettingArrayChildType(property: String): String {
    var innerProperty = KClassName.getLegalClassName(property)
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

/**
 * if the jsonObject maybe a Map Instance
 */
fun maybeJsonObjectBeMapType(jsonObject: JsonObject): Boolean {
    var maybeMapType = true
    if (jsonObject.entrySet().isEmpty()) {
        maybeMapType = false
    } else {
        jsonObject.entrySet().forEach {
            val isPrimitiveNotStringType = try {
                JsonParser().parse(it.key).asJsonPrimitive.isString.not()
            } catch (e: Exception) {
                false
            }
            maybeMapType = isPrimitiveNotStringType and maybeMapType
        }
    }
    return maybeMapType
}

/**
 * get the Key Type of Map type converted from jsonObject
 */
fun getMapKeyTypeConvertFromJsonObject(jsonObject: JsonObject): String {
    val mapKey = getPrimitiveType(JsonParser().parse(jsonObject.entrySet().first().key).asJsonPrimitive)
    return mapKey
}

/**
 * get Map Type Value Type from JsonObject object struct
 */
fun getMapValueTypeConvertFromJsonObject(jsonObject: JsonObject): String {
    var valueType: String = ""
    jsonObject.entrySet().forEach {
        val jsonElement = it.value
        if (jsonElement.isJsonPrimitive) {
            val currentValueType = getPrimitiveType(jsonElement.asJsonPrimitive)
            if (valueType.isEmpty()) {
                valueType = currentValueType
            } else {
                if (currentValueType != valueType) {
                    return DEFAULT_TYPE
                }
            }
        } else if (jsonElement.isJsonObject) {
            if (valueType.isEmpty()) {
                valueType = MAP_DEFAULT_OBJECT_VALUE_TYPE
            } else {
                if (valueType != MAP_DEFAULT_OBJECT_VALUE_TYPE) {
                    return DEFAULT_TYPE
                }
            }
        } else if (jsonElement.isJsonArray) {

            if (valueType.isEmpty()) {
                valueType = getArrayType(MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE, jsonElement.asJsonArray)
            } else {
                if (valueType != getArrayType(MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE, jsonElement.asJsonArray)) {
                    return DEFAULT_TYPE
                }
            }
        }
    }
    if (valueType.isEmpty()) {
        valueType = DEFAULT_TYPE
    }
    return valueType
}
