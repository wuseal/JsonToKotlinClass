package wu.seal.jsontokotlin

import com.google.gson.*

import java.util.HashSet

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinMaker {

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
        for (append in toBeAppend) {
            stringBuilder.append("\n")
            stringBuilder.append(append)
        }
        return stringBuilder.toString()
    }

    private fun appClassName(stringBuilder: StringBuilder) {
        stringBuilder.append("data class ").append(className).append("(\n")
    }


    private fun appendCodeMember(stringBuilder: StringBuilder, jsonObject: JsonObject) {

        for ((property, jsonElementValue) in jsonObject.entrySet()) {

            var type = "String"
            if (jsonElementValue.isJsonArray) {

                type = getArrayType(property, jsonElementValue.asJsonArray)

                if (isExpectedJsonObjArrayType(jsonElementValue.asJsonArray)) {
                    toBeAppend.add(KotlinMaker(type, jsonElementValue.asJsonArray.first()).makeKotlinData())
                }
                addProperty(stringBuilder, property, type, "")

            } else if (jsonElementValue.isJsonPrimitive) {
                type = getPrimitiveType(jsonElementValue)
                addProperty(stringBuilder, property, type, jsonElementValue.asString)

            } else if (jsonElementValue.isJsonObject) {
                type = getJsonObjectType(property)
                toBeAppend.add(KotlinMaker(type, jsonElementValue).makeKotlinData())
                addProperty(stringBuilder, property, type, "")

            } else if (jsonElementValue.isJsonNull) {
                addProperty(stringBuilder, property, type, null)
            }
        }
    }


    private fun addProperty(stringBuilder: StringBuilder, property: String, type: String, value: String?) {
        var innerValue = value
        if (innerValue == null) {
            innerValue = "null"
        }
        stringBuilder.append(KProperty(property, type, innerValue).getPropertyStringBlock())
        stringBuilder.append("\n")
    }

}

fun main(args: Array<String>) {
    isTestModel = true
    val json1 = """{ "progr ammers": [
                { "firstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "aut_hors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

    val json2 = """ {"123menu": {
                "i d": [1,23,34],
                "value":[],
                "popup": {
                "m#@!$#%$#^%*^&(*)*(_)+{|}{:enu_item": [
                {"value": "New", "onclick": "CreateNewDoc()"},
                {"value": "Open", "onclick": "OpenDoc()"},
                {"value": "Close", "onclick": "CloseDoc()"}
                ]}
                }}"""

    println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
    println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")

    TestConfig.targetJsonConvertLib = TargetJsonConverter.None
    TestConfig.isCommentOff = true
    TestConfig.isPropertiesVar = true

    println("===========================================Change to none json lib support========================================= ")

    println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
    println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")

}