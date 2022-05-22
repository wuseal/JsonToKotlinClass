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
    private val jsonArrayExcludeNull = jsonArray.filterOutNullElement()

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
            jsonArrayExcludeNull.allElementAreSamePrimitiveType() -> {

                // if all elements are numbers, we need to select the larger scope of Kotlin types among the elements
                // e.g. [1,2,3.1] should return Double as it's type

                val p = jsonArrayExcludeNull[0].asJsonPrimitive;
                val elementKotlinClass = if(p.isNumber) getKotlinNumberClass(jsonArrayExcludeNull) else p.toKotlinClass()
                LogUtil.i("$tag jsonArray allElementAreSamePrimitiveType, return ListClass with generic type ${elementKotlinClass.name}")
                return ListClass(name = className, generic = elementKotlinClass)
            }
            jsonArrayExcludeNull.allItemAreObjectElement() -> {
                val fatJsonObject = jsonArrayExcludeNull.getFatJsonObject()
                val itemObjClassName = "${className}Item"
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                LogUtil.i("$tag jsonArray allItemAreObjectElement, return ListClass with generic type ${dataClassFromJsonObj.name}")
                return ListClass(className, dataClassFromJsonObj)
            }
            jsonArrayExcludeNull.allItemAreArrayElement() -> {
                val fatJsonArray = jsonArrayExcludeNull.getFatJsonArray()
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
}
