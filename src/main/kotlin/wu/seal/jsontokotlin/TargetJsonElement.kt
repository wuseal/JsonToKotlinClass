package wu.seal.jsontokotlin

import com.google.gson.*
import java.util.*


class TargetJsonElement : ITargetJsonElement {
    private val jsonElement: JsonElement

    constructor(jsonString: String) {
        this.jsonElement = gson.fromJson(jsonString, JsonElement::class.java)
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
            return getFullFieldElementFromArrayElement(jsonArray)
        } else {
            return Gson().toJsonTree(Any())
        }
    }

    companion object {

        private val gson = GsonBuilder().serializeNulls().create()

        /**
         * get an element from the element array , And the result element should contains all the json field in every
         * element of the array
         */
        fun getFullFieldElementFromArrayElement(jsonArray: JsonArray): JsonElement {

            val map = mutableMapOf<String, JsonElement>()

            if (jsonArray[0].isJsonArray) {
                return getFullFieldElementFromArrayElement(jsonArray[0].asJsonArray)
            }

            if (jsonArray[0].isJsonObject.not()) {
                return jsonArray[0]
            }
            jsonArray.forEach {
                if (it.isJsonObject) {
                    val jsObj = it.asJsonObject
                    jsObj.entrySet().forEach { mp ->
                        map[mp.key] = mp.value
                    }
                }
            }

            return gson.fromJson(gson.toJson(map), JsonElement::class.java)
        }
    }

}

