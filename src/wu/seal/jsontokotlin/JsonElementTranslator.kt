package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.util.*

/**
 * Target Json Element Maker form a String or an Element for generating Code
 * Created by Seal.Wu on 2017/9/19.
 */
interface ITargetJsonElement {
    /**
     * get excpected jsonElement for generating kotlin code
     */
    fun getTargetJsonElementForGeneratingCode(): JsonElement
}

fun main(args: Array<String>) {

    val gson = Gson()
    val text1 = """  {"UserID":11, "Name":"Truly", "Email":"zhuleipro◎hotmail.com"}"""
    val text2 = """ [
                    {"UserID":11, "Name":{"FirstName":"Truly","LastName":"Zhu"}, "Email":"zhuleipro◎hotmail.com"},
                    {"UserID":12, "Name":{"FirstName":"Jeffrey","LastName":"Richter"}, "Email":"xxx◎xxx.com"},
                    {"UserID":13, "Name":{"FirstName":"Scott","LastName":"Gu"}, "Email":"xxx2◎xxx2.com"}
                    ]"""
    val text3 = """ [] """

    val text4 = """ 1  """

    val text5 = """[1,2,3]"""

    val text6 = """["1","2","3"]"""

    val text7 = """[1,"2",true]"""


    println("text1  getTargetElement ====>${getTargetElementJson(gson, text1)}")
    println("text2  getTargetElement ====>${getTargetElementJson(gson, text2)}")
    println("text3  getTargetElement ====>${getTargetElementJson(gson, text3)}")

    println("text4  getTargetElement ====>${getTargetElementJson(gson, text4)}")
    println("text5  getTargetElement ====>${getTargetElementJson(gson, text5)}")
    println("text6  getTargetElement ====>${getTargetElementJson(gson, text6)}")
    println("text7  getTargetElement ====>${getTargetElementJson(gson, text7)}")
}

private fun getTargetElementJson(gson: Gson, text1: String):String {
    return try {
        gson.toJson(TargetJsonElement(text1).getTargetJsonElementForGeneratingCode())
    } catch(e: Exception) {
        "UnSupport Json Format String !!"
    }
}


class TargetJsonElement : ITargetJsonElement {
    private val jsonElement: JsonElement

    constructor(jsonString: String) {
        this.jsonElement = JsonParser().parse(jsonString)

    }

    constructor(jsonElement: JsonElement) {
        this.jsonElement = jsonElement
    }

    override fun getTargetJsonElementForGeneratingCode(): JsonElement {
        if (this.jsonElement.isJsonArray) {
            val jsonElementNotArray = getArrayChildElement(this.jsonElement.asJsonArray)
            if (jsonElementNotArray.isJsonObject) {
                return jsonElementNotArray
            }
        } else if (this.jsonElement.isJsonObject) {
            return this.jsonElement
        }
        throw IllegalFormatFlagsException("Unsupported Json String")
    }


    private fun getArrayChildElement(jsonArray: JsonArray): JsonElement {
        if (jsonArray.size() >= 1) {
            val jsonElement1 = jsonArray[0]
            if (jsonElement1.isJsonArray) {
                return getArrayChildElement(jsonElement1.asJsonArray)
            } else {
                return jsonElement1
            }
        } else {
            return Gson().toJsonTree(Any())
        }
    }
}

