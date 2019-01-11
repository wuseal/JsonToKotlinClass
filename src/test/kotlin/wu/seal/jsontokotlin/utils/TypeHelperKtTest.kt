package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.winterbe.expekt.should
import org.junit.Test

/**
 * Created by Seal.Wu on 2018/4/18.
 */
class TypeHelperKtTest {

    @Test
    fun getPrimitiveTypeTest() {
    }

    @Test
    fun getJsonObjectTypeTest() {
    }

    @Test
    fun getChildTypeTest() {
        getChildType("List<List<List<Boy>>>").should.be.equal("Boy")
    }

    @Test
    fun getOutTypeTest() {
    }

    @Test
    fun getRawTypeTest() {
    }

    @Test
    fun getArrayTypeTest() {
        val jsonArrayBool = JsonArray()
        jsonArrayBool.add(true)
        getArrayType("good_friends", jsonArrayBool).should.be.equal("List<Boolean>")

        val jsonArrayInt = JsonArray()
        jsonArrayInt.add(1)
        getArrayType("good_friends", jsonArrayInt).should.be.equal("List<Int>")

        val jsonArrayObj = JsonArray()
        val jsonObject = JsonObject()
        jsonObject.add("name", JsonParser().parse("seal"))
        jsonArrayObj.add(jsonObject)
        getArrayType("good_friends", jsonArrayObj).should.be.equal("List<GoodFriend>")

        val jsonArrayObj1 = JsonArray()
        val jsonObject1 = JsonObject()
        jsonObject1.add("name", JsonParser().parse("seal"))
        jsonArrayObj1.add(jsonObject1)
        getArrayType("show_list", jsonArrayObj1).should.be.equal("List<Show>")
    }

    @Test
    fun isExpectedJsonObjArrayTypeTest() {
        val jsonArray = JsonArray()
        jsonArray.add(true)
        isExpectedJsonObjArrayType(jsonArray).should.be.`false`
        jsonArray.removeAll { true }
        jsonArray.add(1)
        isExpectedJsonObjArrayType(jsonArray).should.be.`false`
        jsonArray.removeAll { true }
        jsonArray.add(JsonObject())
        isExpectedJsonObjArrayType(jsonArray).should.be.`true`
    }

    @Test
    fun adjustPropertyNameForGettingArrayChildTypeTest() {
        adjustPropertyNameForGettingArrayChildType("").should.be.equal("X")
        adjustPropertyNameForGettingArrayChildType("List").should.be.equal("")
        adjustPropertyNameForGettingArrayChildType("arrayList").should.be.equal("Array")
        adjustPropertyNameForGettingArrayChildType("Apples").should.be.equal("Apple")
        adjustPropertyNameForGettingArrayChildType("Activities").should.be.equal("Activity")
        adjustPropertyNameForGettingArrayChildType("Book_List").should.be.equal("Book")
        adjustPropertyNameForGettingArrayChildType("show_list").should.be.equal("Show")
    }

    @Test
    fun maybeJsonObjectBeMapTypeTest() {
        val jsonObjectNotMap = JsonObject()
        jsonObjectNotMap.addProperty("a", 1)
        maybeJsonObjectBeMapType(jsonObjectNotMap).should.be.`false`

        val jsonObjectMap = JsonObject()
        jsonObjectMap.addProperty("1","d")
        jsonObjectMap.addProperty("2","d")
        jsonObjectMap.addProperty("3","d")
        jsonObjectMap.addProperty("4","d")
        jsonObjectMap.addProperty("5","d")
        maybeJsonObjectBeMapType(jsonObjectMap).should.be.`true`


        val jsonObjectNotMapOther = JsonObject()
        jsonObjectNotMapOther.addProperty("1","d")
        jsonObjectNotMapOther.addProperty("2","d")
        jsonObjectNotMapOther.addProperty("a","d")
        jsonObjectNotMapOther.addProperty("4","d")
        jsonObjectNotMapOther.addProperty("5","d")
        maybeJsonObjectBeMapType(jsonObjectNotMapOther).should.be.`false`


        val emptyJsonObject = JsonObject()
        maybeJsonObjectBeMapType(emptyJsonObject).should.be.`false`
    }

    @Test
    fun getMapKeyTypeConvertFromJsonObjectTest() {
        val jsonObjectMapInt = JsonObject()
        jsonObjectMapInt.addProperty("1","d")
        jsonObjectMapInt.addProperty("2","d")
        jsonObjectMapInt.addProperty("3","d")
        jsonObjectMapInt.addProperty("4","d")
        jsonObjectMapInt.addProperty("5","d")

        getMapKeyTypeConvertFromJsonObject(jsonObjectMapInt).should.be.equal(TYPE_INT)
    }

    @Test
    fun getMapValueTypeConvertFromJsonObjectTest() {
        val jsonObjectMapValueString = JsonObject()
        jsonObjectMapValueString.addProperty("1","d")
        jsonObjectMapValueString.addProperty("2","d")
        jsonObjectMapValueString.addProperty("3","d")
        jsonObjectMapValueString.addProperty("4","d")
        jsonObjectMapValueString.addProperty("5","d")

        getMapValueTypeConvertFromJsonObject(jsonObjectMapValueString).should.be.equal("String")

    }



    @Test
    fun getMapValueTypeObjectConvertFromJsonObjectTest() {
        val jsonObjectMapValueObject = JsonObject()
        val jsonObject = JsonObject()
        jsonObject.addProperty("a",324)
        jsonObjectMapValueObject.add("1",jsonObject)
        jsonObjectMapValueObject.add("2",jsonObject)
        jsonObjectMapValueObject.add("3",jsonObject)
        jsonObjectMapValueObject.add("4",jsonObject)
        jsonObjectMapValueObject.add("5",jsonObject)

        getMapValueTypeConvertFromJsonObject(jsonObjectMapValueObject).should.be.equal(MAP_DEFAULT_OBJECT_VALUE_TYPE)

    }

    @Test
    fun getMapValueTypeArrayConvertFromJsonObjectTest() {
        val jsonObjectMapValueArray = JsonObject()
        val jsonObject = JsonObject()
        jsonObject.addProperty("a",324)
        val jsonArray = JsonArray()
        jsonArray.add(jsonObject)
        jsonObjectMapValueArray.add("1",jsonArray)
        jsonObjectMapValueArray.add("2",jsonArray)
        jsonObjectMapValueArray.add("3",jsonArray)
        jsonObjectMapValueArray.add("4",jsonArray)
        jsonObjectMapValueArray.add("5",jsonArray)

        getMapValueTypeConvertFromJsonObject(jsonObjectMapValueArray).should.be.equal("List<$MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE>")

    }
}
