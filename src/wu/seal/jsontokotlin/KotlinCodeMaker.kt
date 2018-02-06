package wu.seal.jsontokotlin

import com.google.gson.*
import wu.seal.jsontokotlin.codeelements.KClassAnnotation
import wu.seal.jsontokotlin.codeelements.KProperty

import java.util.HashSet

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker {

    private var className: String? = null
    private var inputElement: JsonElement? = null

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

        val index = stringBuilder.lastIndexOf(",")
        if (index != -1) {
            stringBuilder.deleteCharAt(index)
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
                stringBuilder.append("\t")
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
        val classAnnotation = KClassAnnotation.getClassAnnotation()
        stringBuilder.append(classAnnotation)
        if (classAnnotation.isNotBlank()) stringBuilder.append("\n")
        stringBuilder.append("data class ").append(className).append("(\n")
    }


    private fun appendCodeMember(stringBuilder: StringBuilder, jsonObject: JsonObject) {

        for ((property, jsonElementValue) in jsonObject.entrySet()) {

            if (jsonElementValue.isJsonArray) {
                val type = getArrayType(property, jsonElementValue.asJsonArray)

                if (isExpectedJsonObjArrayType(jsonElementValue.asJsonArray)) {
                    toBeAppend.add(KotlinCodeMaker(getChildType(getRawType(type)), jsonElementValue.asJsonArray.first()).makeKotlinData())
                }
                addProperty(stringBuilder, property, type, "")

            } else if (jsonElementValue.isJsonPrimitive) {
                val type = getPrimitiveType(jsonElementValue.asJsonPrimitive)
                addProperty(stringBuilder, property, type, jsonElementValue.asString)

            } else if (jsonElementValue.isJsonObject) {
                val type = getJsonObjectType(property)
                toBeAppend.add(KotlinCodeMaker(getRawType(type), jsonElementValue).makeKotlinData())
                addProperty(stringBuilder, property, type, "")

            } else if (jsonElementValue.isJsonNull) {
                addProperty(stringBuilder, property, DEFAULT_TYPE, null)
            }
        }
    }


    private fun addProperty(stringBuilder: StringBuilder, property: String, type: String, value: String?) {
        var innerValue = value
        if (innerValue == null) {
            innerValue = "null"
        }
        stringBuilder.append(KProperty(property, getOutType(type), innerValue).getPropertyStringBlock())
        stringBuilder.append("\n")
    }

}
