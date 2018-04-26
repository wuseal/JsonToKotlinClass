package wu.seal.jsontokotlin

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

    private val indent = getIndent()

    private val toBeAppend = HashSet<String>()

    constructor(className: String, inputText: String) {
        this.inputElement = TargetJsonElement(inputText).getTargetJsonElementForGeneratingCode()
        this.className = className
    }

    constructor(className: String, jsonElement: JsonElement) {
        this.inputElement = TargetJsonElement(jsonElement).getTargetJsonElementForGeneratingCode()
        this.className = className
    }

    fun makeKotlinData(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n")

        val jsonElement = inputElement
        if (jsonElement!!.isJsonObject) {
            appClassName(stringBuilder)
            appendCodeMember(stringBuilder, jsonElement.asJsonObject)
        } else {
            throw IllegalArgumentException("UnSupport")
        }

        stringBuilder.append(")")
        if (toBeAppend.isNotEmpty()) {
            appendSubClassCode(stringBuilder)
        }

        return stringBuilder.toString()
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
        for (append in toBeAppend) {
            stringBuilder.append("\n")
            append.split("\n").filter { it.isNotEmpty() }.forEach {
                stringBuilder.append(indent)
                stringBuilder.append(it)
                stringBuilder.append("\n")
            }
        }
        stringBuilder.append("}")
    }

    private fun appendNormalSubClassCode(stringBuilder: StringBuilder) {
        for (append in toBeAppend) {
            stringBuilder.append("\n")
            stringBuilder.append(append)
        }
    }

    private fun appClassName(stringBuilder: StringBuilder) {
        val classAnnotation = KClassAnnotation.getClassAnnotation(className.toString())
        stringBuilder.append(classAnnotation)
        if (classAnnotation.isNotBlank()) stringBuilder.append("\n")
        stringBuilder.append("data class ").append(className).append("(\n")
    }


    private fun appendCodeMember(stringBuilder: StringBuilder, jsonObject: JsonObject) {

        val size = jsonObject.size()

        jsonObject.entrySet().forEachIndexed { index, (property, jsonElementValue) ->
            val isLast = (index == size - 1)

            if (jsonElementValue.isJsonArray) {
                val type = getArrayType(property, jsonElementValue.asJsonArray)

                if (isExpectedJsonObjArrayType(jsonElementValue.asJsonArray)) {
                    toBeAppend.add(KotlinCodeMaker(getChildType(getRawType(type)), jsonElementValue).makeKotlinData())
                }
                addProperty(stringBuilder, property, type, "", isLast)

            } else if (jsonElementValue.isJsonPrimitive) {
                val type = getPrimitiveType(jsonElementValue.asJsonPrimitive)
                addProperty(stringBuilder, property, type, jsonElementValue.asString, isLast)

            } else if (jsonElementValue.isJsonObject) {
                if (ConfigManager.enableMapType && maybeJsonObjectBeMapType(jsonElementValue.asJsonObject)) {
                    val mapKeyType = getMapKeyTypeConvertFromJsonObject(jsonElementValue.asJsonObject)
                    val mapValueType = getMapValueTypeConvertFromJsonObject(jsonElementValue.asJsonObject)
                    if (mapValueType == MAP_DEFAULT_OBJECT_VALUE_TYPE
                            || mapValueType.contains(MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE)
                    ) {
                        toBeAppend.add(
                                KotlinCodeMaker(
                                        getChildType(mapValueType),
                                        jsonElementValue.asJsonObject.entrySet().first().value
                                ).makeKotlinData()
                        )
                    }
                    val mapType = "Map<$mapKeyType,$mapValueType>"
                    addProperty(stringBuilder, property, mapType, "", isLast)

                } else {
                    val type = getJsonObjectType(property)
                    toBeAppend.add(KotlinCodeMaker(getRawType(type), jsonElementValue).makeKotlinData())
                    addProperty(stringBuilder, property, type, "", isLast)
                }

            } else if (jsonElementValue.isJsonNull) {
                addProperty(stringBuilder, property, DEFAULT_TYPE, null, isLast)
            }
        }
    }


    private fun addProperty(stringBuilder: StringBuilder, property: String, type: String, value: String?, isLast: Boolean = false) {
        var innerValue = value
        if (innerValue == null) {
            innerValue = "null"
        }
        val p = KProperty(property, getOutType(type, value), innerValue)

        stringBuilder.append(p.getPropertyStringBlock())

        if (!isLast)
            stringBuilder.append(",")

        stringBuilder.append(" // ")
                .append(p.getPropertyComment())
                .append("\n")
    }

}
