package wu.seal.jsontokotlin.utils.classgenerator

import com.google.gson.Gson
import com.google.gson.JsonArray
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-23
 * Generate `List<$ItemType>` from json array string and json array's json key
 */
class GenericListClassGeneratorByJSONArray(private val jsonKey: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = readJsonArray(jsonArrayString)
    private val jsonArrayExcludeNull = jsonArray.filterOutNullElement()
    private val hasNulls get() = jsonArray.size() != jsonArrayExcludeNull.size()


    fun generate(): GenericListClass {

        when {
            jsonArray.size() == 0 -> {
                LogUtil.i("$tag jsonArray size is 0, return GenericListClass with generic type ANY")
                return GenericListClass(generic = KotlinClass.ANY, nullableElements = true)
            }
            jsonArray.allItemAreNullElement() -> {
                LogUtil.i("$tag jsonArray allItemAreNullElement, return GenericListClass with generic type ${KotlinClass.ANY.name}")
                return GenericListClass(generic = KotlinClass.ANY, nullableElements = true)
            }
            jsonArrayExcludeNull.allElementAreSamePrimitiveType() -> {
                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArrayExcludeNull[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArrayExcludeNull) else p.toKotlinClass()
                LogUtil.i("$tag jsonArray allElementAreSamePrimitiveType, return GenericListClass with generic type ${elementKotlinClass.name}")
                return GenericListClass(generic = elementKotlinClass, nullableElements = hasNulls)
            }
            jsonArrayExcludeNull.allItemAreObjectElement() -> {
                val fatJsonObject = jsonArrayExcludeNull.getFatJsonObject()
                val itemObjClassName = getRecommendItemName(jsonKey)
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                LogUtil.i("$tag jsonArray allItemAreObjectElement, return GenericListClass with generic type ${dataClassFromJsonObj.name}")
                return GenericListClass(generic = dataClassFromJsonObj, nullableElements = hasNulls)
            }
            jsonArrayExcludeNull.allItemAreArrayElement() -> {
                val fatJsonArray = jsonArrayExcludeNull.getFatJsonArray()
                val genericListClassFromFatJsonArray = GenericListClassGeneratorByJSONArray(jsonKey, fatJsonArray.toString()).generate()
                LogUtil.i("$tag jsonArray allItemAreArrayElement, return GenericListClass with generic type ${genericListClassFromFatJsonArray.name}")
                return GenericListClass(generic = genericListClassFromFatJsonArray, nullableElements = hasNulls)
            }
            else -> {
                LogUtil.i("$tag jsonArray exception shouldn't come here, return GenericListClass with generic type ANY")
                return GenericListClass(generic = KotlinClass.ANY, nullableElements = true)
            }
        }
    }

    private fun getRecommendItemName(jsonKey: String): String {
        return adjustPropertyNameForGettingArrayChildType(jsonKey)
    }
}

internal fun readJsonArray(jsonArrayString: String): JsonArray {
    val jsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)
    // if the last element is null, it may actually be a trailing comma
    if (jsonArray.size() == 0 || !jsonArray.last().isJsonNull) {
        return jsonArray
    }
    // check if the json array has a trailing comma
    if (jsonArrayString.trimEnd().removeSuffix("]").trimEnd().endsWith(",")) {
        val lastComma = jsonArrayString.lastIndexOf(",")
        return Gson().fromJson(jsonArrayString.removeRange(lastComma..lastComma), JsonArray::class.java)
    } else {
        return jsonArray
    }
}
