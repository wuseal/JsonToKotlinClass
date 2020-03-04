package wu.seal.jsontokotlin.utils

import com.google.gson.*

/**
 * This class aim at filtering out the expected Json Element to be convert from Json array
 *
 */
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
            val jsonArrayWithoutNullElement = jsonElement.asJsonArray.filterOutNullElement()
            if (jsonArrayWithoutNullElement.size() == 0) {
                return gson.toJsonTree(Any())
            } else if (allElementAreObject(jsonArrayWithoutNullElement)) {
                val jsonElementNotArray = getArrayChildElement(jsonArrayWithoutNullElement)
                if (jsonElementNotArray.isJsonObject) {
                    return jsonElementNotArray
                } else {
                    throw IllegalStateException("Unbelievable！ should not throw out this exception")
                }
            } else if (jsonArrayWithoutNullElement.onlyHasOneElementRecursive() || jsonArrayWithoutNullElement.onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive()) {
                return getArrayChildElement(jsonArrayWithoutNullElement)
            } else if (allElementAreSamePrimitiveType(jsonArrayWithoutNullElement)) {
                return jsonArrayWithoutNullElement[0]
            } else {
                return gson.toJsonTree(Any())
            }
        } else if (this.jsonElement.isJsonObject) {
            return this.jsonElement
        } else if (this.jsonElement.isJsonPrimitive) {
            return this.jsonElement
        } else {
            return this.jsonElement
        }
    }


    private fun allElementAreObject(jsonArray: JsonArray): Boolean {
        var allElementAreObject = true
        jsonArray.forEach {
            if (it.isJsonObject.not()) {
                allElementAreObject = false
                return@forEach
            }
        }
        return allElementAreObject
    }

    private fun allElementAreSamePrimitiveType(jsonArray: JsonArray): Boolean {
        var allElementAreSamePrimitiveType = true

            jsonArray.forEach {
                if (it.isJsonPrimitive.not()) {
                    allElementAreSamePrimitiveType = false
                    return allElementAreSamePrimitiveType
                }
                if (theSamePrimitiveType(jsonArray[0].asJsonPrimitive, it.asJsonPrimitive).not()) {
                    allElementAreSamePrimitiveType = false
                    return allElementAreSamePrimitiveType
                }
            }


        return allElementAreSamePrimitiveType
    }

    private fun theSamePrimitiveType(first: JsonPrimitive, second: JsonPrimitive): Boolean {

        val sameBoolean = first.isBoolean && second.isBoolean

        val sameNumber = first.isNumber && second.isNumber

        val sameString = first.isString && second.isString

        return sameBoolean || sameNumber || sameString
    }

    private fun getArrayChildElement(jsonArray: JsonArray): JsonElement {
        return if (jsonArray.size() >= 1) {
            getFullFieldElementFromArrayElement(jsonArray)
        } else {
            Gson().toJsonTree(Any())
        }
    }

    companion object {

        private val gson = GsonBuilder().serializeNulls().create()

        /**
         * get an element from the element array , And the result element should contains all the json field in every
         * element of the array
         *
         * the input argument jsonArray should only contains jsonObject or only contains one element Recursive like [[["element"]]]
         */
        fun getFullFieldElementFromArrayElement(jsonArray: JsonArray): JsonElement {

            val map = mutableMapOf<String, JsonElement>()
            val nullableProperties = mutableSetOf<String>()

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
                        if (mp.value.isJsonNull) {
                            nullableProperties.add(mp.key)
                        } else {
                            map[mp.key] = mp.value
                        }
                    }
                }
            }

            nullableProperties.forEach {
                if (map.containsKey(it)) {
                    map[it + BACKSTAGE_NULLABLE_POSTFIX] = JsonNull.INSTANCE
                } else {
                    map[it] = JsonNull.INSTANCE
                }
            }

            return gson.fromJson(gson.toJson(map), JsonElement::class.java)
        }


    }

}

