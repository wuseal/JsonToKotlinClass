package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import wu.seal.jsontokotlin.bean.jsonschema.GSON
import wu.seal.jsontokotlin.bean.jsonschema.JsonSchema
import wu.seal.jsontokotlin.codeelements.KProperty
import wu.seal.jsontokotlin.utils.*
import java.util.*

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker {

    private var className: String
    private var inputText: String
    private var inputElement: JsonElement

    private var originElement: JsonElement

    private val indent = getIndent()

    private val toBeAppend = HashSet<String>()

    constructor(className: String, inputText: String) {
        originElement = Gson().fromJson<JsonElement>(inputText, JsonElement::class.java)
        this.inputElement = TargetJsonElement(inputText).getTargetJsonElementForGeneratingCode()
        this.className = className
        this.inputText = inputText
    }

    constructor(className: String, jsonElement: JsonElement) {
        originElement = jsonElement
        this.inputElement = TargetJsonElement(jsonElement).getTargetJsonElementForGeneratingCode()
        this.className = className
        this.inputText = jsonElement.toString()
    }

    private fun parseJSONSchemaOrNull(className: String, json: String) : String? {
        return try {
            val jsonSchema = GSON.fromJson<JsonSchema>(json, JsonSchema::class.java)
            if (jsonSchema.schema?.isNotBlank() != true) {
                throw IllegalArgumentException("input string is not valid json schema")
            }
            val generator = JsonSchemaDataClassGenerator(jsonSchema)
            generator.generate(className)
            generator.classes.joinToString("\n") { it.toString() }
        } catch (t: Throwable) {
            null
        }
    }

    private fun parseJSONString() : String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n")

        val jsonElement = inputElement
        checkIsNotEmptyObjectJSONElement(jsonElement)

        appendClassName(stringBuilder)
        appendCodeMember(stringBuilder, jsonElement.asJsonObject!!)

        stringBuilder.append(")")
        if (toBeAppend.isNotEmpty()) {
            appendSubClassCode(stringBuilder)
        }

        return stringBuilder.toString()
    }

    fun makeKotlinData(): String {
        return parseJSONSchemaOrNull(className, inputText)
            ?: parseJSONString()
    }

    //the fucking code
    private fun checkIsNotEmptyObjectJSONElement(jsonElement: JsonElement?) {
        if (jsonElement!!.isJsonObject) {
            if (jsonElement.asJsonObject.entrySet().isEmpty() && originElement.isJsonArray) {
                //when [[[{}]]]
                if (originElement.asJsonArray.onlyHasOneElementRecursive()) {
                    val unSupportJsonException = UnSupportJsonException("Unsupported Json String")
                    val adviceType =
                        getArrayType("Any", originElement.asJsonArray).replace(Regex("Int|Float|String|Boolean"), "Any")
                    unSupportJsonException.adviceType = adviceType
                    unSupportJsonException.advice =
                            """No need converting, just use $adviceType is enough for your json string"""
                    throw unSupportJsonException
                } else {
                    //when [1,"a"]
                    val unSupportJsonException = UnSupportJsonException("Unsupported Json String")
                    unSupportJsonException.adviceType = "List<Any>"
                    unSupportJsonException.advice =
                            """No need converting,  List<Any> may be a good class type for your json string"""
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
            unSupportJsonException.advice =
                    """No need converting, just use $adviceType is enough for your json string"""
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
        if (inputElement.isJsonNull || (inputElement as? JsonObject)?.entrySet()?.isEmpty() == true) {
            stringBuilder.append("class ").append(className).append("(\n")
        } else {
            stringBuilder.append("data class ").append(className).append("(\n")
        }
    }


    private fun appendCodeMember(stringBuilder: StringBuilder, jsonObject: JsonObject) {
        val size = jsonObject.entrySet().size
        jsonObject.entrySet().forEachIndexed { index, (property, jsonElementValue) ->

            val isLast = (index == size - 1)
            when {
                jsonElementValue.isJsonNull -> addProperty(stringBuilder, property, DEFAULT_TYPE, null, isLast)

                jsonElementValue.isJsonPrimitive -> {
                    val type = getPrimitiveType(jsonElementValue.asJsonPrimitive)
                    addProperty(stringBuilder, property, type, jsonElementValue.asString, isLast)
                }

                jsonElementValue.isJsonArray -> {
                    jsonElementValue.asJsonArray.run {
                        var type = getArrayType(property, this)
                        if(!allChildrenAreEmptyArray()) {
                            if (isExpectedJsonObjArrayType(this) || onlyHasOneObjectElementRecursive()
                                    || onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive()
                            ) {
                                val subCode = try {
                                    KotlinCodeMaker(getChildType(getRawType(type)), jsonElementValue).makeKotlinData()
                                } catch (e: UnSupportJsonException) {
                                    type = e.adviceType
                                    ""
                                }
                                toBeAppend.add(subCode)
                            }
                        }
                        addProperty(stringBuilder, property, type, "", isLast)
                    }
                }

                jsonElementValue.isJsonObject -> {
                    jsonElementValue.asJsonObject.run {
                        if (ConfigManager.enableMapType && maybeJsonObjectBeMapType(this)) {
                            val mapKeyType = getMapKeyTypeConvertFromJsonObject(this)
                            var mapValueType = getMapValueTypeConvertFromJsonObject(this)
                            if (mapValueIsObjectType(mapValueType)) {
                                val subCode = try {
                                    KotlinCodeMaker(
                                        getChildType(mapValueType),
                                        entrySet().first().value
                                    ).makeKotlinData()
                                } catch (e: UnSupportJsonException) {
                                    mapValueType = e.adviceType
                                    ""
                                }
                                toBeAppend.add(subCode)
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
                    }
                }
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

        val p = KProperty(property, type, value ?: "null")
        stringBuilder.apply {
            append(p.getPropertyStringBlock())
            if (!isLast) {
                append(",")
            }
            val propertyComment = p.getPropertyComment()
            if (propertyComment.isNotBlank()) {
                append(" // ")
                append(getCommentCode(propertyComment))
            }
            append("\n")
        }
    }

}
