package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import wu.seal.jsontokotlin.codeelements.KClassAnnotation
import wu.seal.jsontokotlin.codeelements.KProperty
import wu.seal.jsontokotlin.utils.*
import java.util.*

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker {

    private var className: String? = null
    private var inputElement: JsonElement? = null

    private var originElement: JsonElement

    private val indent = getIndent()

    private val toBeAppend = HashSet<String>()

    constructor(className: String, inputText: String) {
        originElement = Gson().fromJson<JsonElement>(inputText, JsonElement::class.java)
        this.inputElement = TargetJsonElement(inputText).getTargetJsonElementForGeneratingCode()
        this.className = className
    }

    constructor(className: String, jsonElement: JsonElement) {
        originElement = jsonElement
        this.inputElement = TargetJsonElement(jsonElement).getTargetJsonElementForGeneratingCode()
        this.className = className
    }

    fun makeKotlinData(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n")

        val jsonElement = inputElement
        checkIsNotEmptyObjectJSONElement(jsonElement)

        appendClassName(stringBuilder)
        appendCodeMember(stringBuilder, jsonElement?.asJsonObject!!)

        stringBuilder.append(")")
        if (toBeAppend.isNotEmpty()) {
            appendSubClassCode(stringBuilder)
        }

        return stringBuilder.toString()
    }

    //the fucking code
    private fun checkIsNotEmptyObjectJSONElement(jsonElement: JsonElement?) {
        if (jsonElement!!.isJsonObject) {
            if (jsonElement.asJsonObject.entrySet().isEmpty() && originElement.isJsonArray) {
                //when [[[{}]]]
                if (originElement.asJsonArray.onlyHasOneElementRecursive()) {
                    val unSupportJsonException = UnSupportJsonException("Unsupported Json String")
                    val adviceType = getArrayType("Any", originElement.asJsonArray).replace(Regex("Int|Float|String|Boolean"), "Any")
                    unSupportJsonException.adviceType = adviceType
                    unSupportJsonException.advice = """No need converting, just use $adviceType is enough for your json string"""
                    throw unSupportJsonException
                } else {
                    //when [1,"a"]
                    val unSupportJsonException = UnSupportJsonException("Unsupported Json String")
                    unSupportJsonException.adviceType = "List<Any>"
                    unSupportJsonException.advice = """No need converting,  List<Any> may be a good class type for your json string"""
                    throw unSupportJsonException
                }
            }
        } else {
            /**
             * in this condition the only result it that we just give the json a List<Any> type is enough, No need to
             * do any convert to make class type
             */
            val unSupportJsonException = UnSupportJsonException("Unsupported Json String")
            val adviceType = getArrayType("Any", originElement.asJsonArray).replace("AnyX", "Any")
            unSupportJsonException.advice = """No need converting, just use $adviceType is enough for your json string"""
            throw unSupportJsonException
        }
    }

    private fun appendSubClassCode(stringBuilder: StringBuilder) {
        if (ConfigManager.isInnerClassModel) {
            appendInnerClassModelSubClassCode(stringBuilder)
        } else {
            appendNormalSubClassCode(stringBuilder)
        }
    }

    private fun appendInnerClassModelSubClassCode(stringBuilder: StringBuilder) {
        stringBuilder.append(" {")
        stringBuilder.append("\n")
        val nestedClassCode = toBeAppend.joinToString("\n\n") {
            it.split("\n").joinToString("\n") { line ->
                indent + line
            }
        }
        stringBuilder.append(nestedClassCode)
        stringBuilder.append("\n}")
    }

    private fun appendNormalSubClassCode(stringBuilder: StringBuilder) {
        for (append in toBeAppend) {
            stringBuilder.append("\n")
            stringBuilder.append(append)
        }
    }

    private fun appendClassName(stringBuilder: StringBuilder) {
        if (inputElement?.isJsonNull == true || (inputElement as? JsonObject)?.entrySet()?.isEmpty() == true) {
            stringBuilder.append("class ").append(className).append("(\n")
        } else {
            stringBuilder.append("data class ").append(className).append("(\n")
        }
    }


    private fun appendCodeMember(stringBuilder: StringBuilder, jsonObject: JsonObject) {

        val size = jsonObject.entrySet().size

        val entryList =
                if (ConfigManager.isOrderByAlphabetical) jsonObject.entrySet().sortedBy { it.key }
                else jsonObject.entrySet()
        entryList.forEachIndexed { index, (property, jsonElementValue) ->
            val isLast = (index == size - 1)

            if (jsonElementValue.isJsonArray) {
                var type = getArrayType(property, jsonElementValue.asJsonArray)

                if (isExpectedJsonObjArrayType(jsonElementValue.asJsonArray) || jsonElementValue.asJsonArray.onlyHasOneObjectElementRecursive()
                        || jsonElementValue.asJsonArray.onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive()) {

                    val subCode = try {
                        KotlinCodeMaker(getChildType(getRawType(type)), jsonElementValue).makeKotlinData()
                    } catch (e: UnSupportJsonException) {
                        type = e.adviceType
                        ""
                    }
                    toBeAppend.add(subCode)
                }
                addProperty(stringBuilder, property, type, "", isLast)

            } else if (jsonElementValue.isJsonPrimitive) {
                val type = getPrimitiveType(jsonElementValue.asJsonPrimitive)
                addProperty(stringBuilder, property, type, jsonElementValue.asString, isLast)

            } else if (jsonElementValue.isJsonObject) {
                if (ConfigManager.enableMapType && maybeJsonObjectBeMapType(jsonElementValue.asJsonObject)) {
                    val mapKeyType = getMapKeyTypeConvertFromJsonObject(jsonElementValue.asJsonObject)
                    var mapValueType = getMapValueTypeConvertFromJsonObject(jsonElementValue.asJsonObject)
                    if (mapValueIsObjectType(mapValueType)) {
                        val subCode = try {
                            KotlinCodeMaker(
                                    getChildType(mapValueType),
                                    jsonElementValue.asJsonObject.entrySet().first().value
                            ).makeKotlinData()
                        } catch (e: UnSupportJsonException) {
                            mapValueType = e.adviceType
                            ""
                        }
                        toBeAppend.add(
                                subCode
                        )
                    }
                    val mapType = "Map<$mapKeyType,$mapValueType>"
                    addProperty(stringBuilder, property, mapType, "", isLast)

                } else {
                    var type = getJsonObjectType(property)
                    val subCode = try {
                        KotlinCodeMaker(getRawType(type), jsonElementValue).makeKotlinData()
                    } catch (e: UnSupportJsonException) {
                        type = e.adviceType
                        ""
                    }
                    toBeAppend.add(subCode)
                    addProperty(stringBuilder, property, type, "", isLast)
                }

            } else if (jsonElementValue.isJsonNull) {
                addProperty(stringBuilder, property, DEFAULT_TYPE, null, isLast)
            }
        }
    }

    private fun mapValueIsObjectType(mapValueType: String) = (mapValueType == MAP_DEFAULT_OBJECT_VALUE_TYPE
            || mapValueType.contains(MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE))


    private fun addProperty(
            stringBuilder: StringBuilder,
            property: String,
            type: String,
            value: String?,
            isLast: Boolean = false
    ) {
        var innerValue = value
        if (innerValue == null) {
            innerValue = "null"
        }
        val p = KProperty(property, getOutType(type, value), innerValue)

        stringBuilder.append(p.getPropertyStringBlock())

        if (!isLast)
            stringBuilder.append(",")

        val propertyComment = p.getPropertyComment()
        if (propertyComment.isNotBlank())
            stringBuilder.append(" // ")
                    .append(getCommentCode(propertyComment))
        stringBuilder.append("\n")
    }

}
