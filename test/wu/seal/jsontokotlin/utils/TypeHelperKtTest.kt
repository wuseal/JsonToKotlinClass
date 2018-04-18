package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Assert.*

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
}