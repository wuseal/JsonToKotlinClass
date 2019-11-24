package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import wu.seal.jsontokotlin.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-23
 * Generate `List<$ItemType>` from json array string and json array's json key
 */
class GenericListClassGeneratorByJSONArray(private val jsonKey: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)

    fun generate(): GenericListClass {

        when {
            jsonArray.size() == 0 -> {
                LogUtil.i("$tag jsonArray size is 0, return GenericListClass with generic type ANY")
                return GenericListClass(generic = KotlinClass.ANY)
            }
            jsonArray.allItemAreNullElement() -> {
                LogUtil.i("$tag jsonArray allItemAreNullElement, return GenericListClass with generic type ${KotlinClass.ANY.name}")
                return GenericListClass(generic = KotlinClass.ANY)
            }
            jsonArray.allElementAreSamePrimitiveType() -> {
                val elementKotlinClass = jsonArray[0].asJsonPrimitive.toKotlinClass()
                LogUtil.i("$tag jsonArray allElementAreSamePrimitiveType, return GenericListClass with generic type ${elementKotlinClass.name}")
                return GenericListClass(generic = elementKotlinClass)
            }
            jsonArray.allItemAreObjectElement() -> {
                val fatJsonObject = getFatJsonObject(jsonArray)
                val itemObjClassName = getRecommendItemName(jsonKey)
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                LogUtil.i("$tag jsonArray allItemAreObjectElement, return GenericListClass with generic type ${dataClassFromJsonObj.name}")
                return GenericListClass(generic = dataClassFromJsonObj)
            }
            jsonArray.allItemAreArrayElement() -> {
                val fatJsonArray = getFatJsonArray(jsonArray)
                val genericListClassFromFatJsonArray = GenericListClassGeneratorByJSONArray(jsonKey, fatJsonArray.toString()).generate()
                LogUtil.i("$tag jsonArray allItemAreArrayElement, return GenericListClass with generic type ${genericListClassFromFatJsonArray.name}")
                return GenericListClass(generic =  genericListClassFromFatJsonArray)
            }
            else -> {
                LogUtil.i("$tag jsonArray exception shouldn't come here, return GenericListClass with generic type ANY")
                return GenericListClass(generic = KotlinClass.ANY)
            }
        }
    }

    private fun getRecommendItemName(jsonKey: String): String {
        return adjustPropertyNameForGettingArrayChildType(jsonKey)
    }

    private fun getFatJsonArray(jsonArray: JsonArray): JsonArray {
        if (jsonArray.size() == 0 || !jsonArray.allItemAreArrayElement()) {
            throw IllegalStateException("input arg jsonArray must not be empty and all element should be json array! ")
        }
        val fatJsonArray = JsonArray()
        jsonArray.forEach {
            fatJsonArray.addAll(it.asJsonArray)
        }
        return fatJsonArray
    }


    /**
     * get a Fat JsonObject whose fields contains all the objects' fields around the objects of the json array
     */
    private fun getFatJsonObject(jsonArray: JsonArray): JsonObject {
        if (jsonArray.size() == 0 || !jsonArray.allItemAreObjectElement()) {
            throw IllegalStateException("input arg jsonArray must not be empty and all element should be json object! ")
        }
        val allFields = jsonArray.flatMap { it.asJsonObject.entrySet().map { entry -> Pair(entry.key, entry.value) } }
        val fatJsonObject = JsonObject()
        allFields.forEach { (key, value) ->
            if (value is JsonNull && fatJsonObject.has(key)) {
                //if the value is null and pre added the same key into the fatJsonObject,
                // then translate it to a new special property to indicate that the property is nullable
                //later will consume this property (do it here[DataClassGeneratorByJSONObject#consumeBackstageProperties])
                // delete it or translate it back to normal property without [BACKSTAGE_NULLABLE_POSTFIX] when consume it
                // and will not be generated in final code
                fatJsonObject.add(key + BACKSTAGE_NULLABLE_POSTFIX, value)
            } else {
                fatJsonObject.add(key, value)
            }
        }
        return fatJsonObject
    }

}
