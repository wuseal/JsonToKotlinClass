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

