package wu.seal.jsontokotlin.utils.classgenerator

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-23
 * Generate `List<$ItemType>` from json array string and json array's json key
 */
class GenericListClassGeneratorByJSONArray(private val jsonKey: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)
    private val jsonArrayExcludeNull = jsonArray.filterOutNullElement()


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
            jsonArrayExcludeNull.allElementAreSamePrimitiveType() -> {
                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArrayExcludeNull[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArrayExcludeNull) else p.toKotlinClass()
                LogUtil.i("$tag jsonArray allElementAreSamePrimitiveType, return GenericListClass with generic type ${elementKotlinClass.name}")
                return GenericListClass(generic = elementKotlinClass)
            }
            jsonArrayExcludeNull.allItemAreObjectElement() -> {
                val fatJsonObject = jsonArrayExcludeNull.getFatJsonObject()
                val itemObjClassName = getRecommendItemName(jsonKey)
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                LogUtil.i("$tag jsonArray allItemAreObjectElement, return GenericListClass with generic type ${dataClassFromJsonObj.name}")
                return GenericListClass(generic = dataClassFromJsonObj)
            }
            jsonArrayExcludeNull.allItemAreArrayElement() -> {
                val fatJsonArray = jsonArrayExcludeNull.getFatJsonArray()
                val genericListClassFromFatJsonArray = GenericListClassGeneratorByJSONArray(jsonKey, fatJsonArray.toString()).generate()
                LogUtil.i("$tag jsonArray allItemAreArrayElement, return GenericListClass with generic type ${genericListClassFromFatJsonArray.name}")
                return GenericListClass(generic = genericListClassFromFatJsonArray)
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
}
