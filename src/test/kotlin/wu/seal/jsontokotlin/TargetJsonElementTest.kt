package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig.isTestModel
import wu.seal.jsontokotlin.utils.TargetJsonElement

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class TargetJsonElementTest {

    val error = "UnSupport Json Format String !!"

    @Before
    fun setUp() {
        isTestModel = true
    }

    @Test
    fun getTargetJsonElementForGeneratingCode() {
        val gson = Gson()
        val text1 = """  {"UserID":11, "Name":"Truly", "Email":"zhuleipro◎hotmail.com"}"""
        val text2 = """ [
                    {"UserID":11, "Name":{"FirstName":"Truly","LastName":"Zhu"}, "Email":"zhuleipro◎hotmail.com"},
                    {"UserID":12, "Name":{"FirstName":"Jeffrey","LastName":"Richter"}, "Email":"xxx◎xxx.com"},
                    {"UserID":13, "Name":{"FirstName":"Scott","LastName":"Gu"}, "Email":"xxx2◎xxx2.com"}
                    ]"""
        val text3 = """ [] """

        val text4 = """ 1  """

        val text5 = """[1,2,3]"""

        val text6 = """["1","2","3"]"""

        val text7 = """[1,"2",true]"""

        val text8 = """[[[1]]]"""

        val text9 = """[[1,2],[]]"""


        val text10 = """[
          {
            "distTypeId": "55f40fa5-b6b4-4dcf-a963-52a57f53a71e",
            "distTypeName": "มแี ผง"
          },
            "..."
          ]"""


        val targetElementJson1 = getTargetElementJson(gson, text1)
        targetElementJson1.should.be.equal(gson.toJson(gson.fromJson(text1, JsonElement::class.java)))

        val targetElementJson2 = getTargetElementJson(gson, text2)
        targetElementJson2.should.be.equal(gson.toJson(gson.fromJson(text2, JsonArray::class.java)[2]))

        val targetElementJson3 = getTargetElementJson(gson, text3)
        targetElementJson3.should.be.equal(gson.toJson(Any()))

        val targetElementJson4 = getTargetElementJson(gson, text4)
        targetElementJson4.should.be.equal("1")

        val targetElementJson5 = getTargetElementJson(gson, text5)
        targetElementJson5.should.be.equal("1")

        val targetElementJson6 = getTargetElementJson(gson, text6)
        targetElementJson6.should.be.equal("\"1\"")

        val targetElementJson7 = getTargetElementJson(gson, text7)
        targetElementJson7.should.be.equal("{}")

        val targetElementJson8 = getTargetElementJson(gson, text8)
        targetElementJson8.should.be.equal("1")

        val targetElementJson9 = getTargetElementJson(gson, text9)
        targetElementJson9.should.be.equal("{}")

        val targetElementJson10 = getTargetElementJson(gson, text10)
        targetElementJson10.should.be.equal("{}")

    }

    private fun getTargetElementJson(gson: Gson, text1: String): String {
        return try {
            gson.toJson(TargetJsonElement(text1).getTargetJsonElementForGeneratingCode())
        } catch(e: Exception) {
            e.printStackTrace()
            error
        }
    }


    @Test
    fun testGetFullFieldElementFromInArrayElement() {
        val json ="""[{"name":"MEDICAL CONDITION","show":true,"id":1},{"name":"LAB TEST REPORT","show":true,"id":9,"type":[{"id":21,"name":"CULTURE REPORT","show":true}]}]

"""
        val expectedResult = """{
  "name": "LAB TEST REPORT",
  "show": true,
  "id": 9,
  "type": [
    {
      "id": 21,
      "name": "CULTURE REPORT",
      "show": true
    }
  ]
}"""
        val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
        val fullFieldElement = TargetJsonElement.getFullFieldElementFromArrayElement(gson.fromJson(json, JsonArray::class.java))
        val result = gson.toJson(fullFieldElement)
        result.should.be.equal(expectedResult)
        println(result)

    }
}