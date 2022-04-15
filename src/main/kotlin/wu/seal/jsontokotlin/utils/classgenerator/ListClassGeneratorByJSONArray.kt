package wu.seal.jsontokotlin.utils.classgenerator

import com.google.gson.*
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.ListClass
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-11-20
 * Generate List Class from JsonArray String
 */
class ListClassGeneratorByJSONArray(private val className: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)

    fun generate(): ListClass {

        when {
            jsonArray.size() == 0 -> {
                LogUtil.i("$tag jsonArray size is 0, return ListClass with generic type ANY")
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArray.allItemAreNullElement() -> {
                LogUtil.i("$tag jsonArray allItemAreNullElement, return ListClass with generic type ${KotlinClass.ANY.name}")
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArray.allElementAreSamePrimitiveType() -> {

                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArray[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArray) else p.toKotlinClass()
                LogUtil.i("$tag jsonArray allElementAreSamePrimitiveType, return ListClass with generic type ${elementKotlinClass.name}")
                return ListClass(name = className, generic = elementKotlinClass)
            }
            jsonArray.allItemAreObjectElement() -> {
                val fatJsonObject = getFatJsonObject(jsonArray)
                val itemObjClassName = "${className}Item"
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                LogUtil.i("$tag jsonArray allItemAreObjectElement, return ListClass with generic type ${dataClassFromJsonObj.name}")
                return ListClass(className, dataClassFromJsonObj)
            }
            jsonArray.allItemAreArrayElement() -> {
                val fatJsonArray = getFatJsonArray(jsonArray)
                val itemArrayClassName = "${className}SubList"
                val listClassFromFatJsonArray = ListClassGeneratorByJSONArray(itemArrayClassName, fatJsonArray.toString()).generate()
                LogUtil.i("$tag jsonArray allItemAreArrayElement, return ListClass with generic type ${listClassFromFatJsonArray.name}")
                return ListClass(className, listClassFromFatJsonArray)
            }
            else -> {
                LogUtil.i("$tag jsonArray exception shouldn't come here, return ListClass with generic type ANY")
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
        }
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
            } else if (fatJsonObject.has(key) && value is JsonPrimitive && value.isNumber
                    && fatJsonObject[key].isJsonPrimitive && fatJsonObject[key].asJsonPrimitive.isNumber) {
                    //when the the field is a number type, we need to select the value with largest scope
                    //e.g.  given [{"key":10},{"key":11.2}]
                    //we should use the object with value = 11.2 to represent the object type which will be Double

                    val prev = fatJsonObject[key].asJsonPrimitive
                    val cur = value.asJsonPrimitive
                    if(cur.toKotlinClass().getNumLevel() > prev.toKotlinClass().getNumLevel()) {
                        fatJsonObject.add(key, value);
                    }
            } else {
                fatJsonObject.add(key, value)
            }
        }
        return fatJsonObject
    }

}
