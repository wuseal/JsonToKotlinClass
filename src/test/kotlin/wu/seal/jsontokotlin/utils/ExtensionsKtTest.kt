package wu.seal.jsontokotlin.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.winterbe.expekt.should
import org.junit.Test

class ExtensionsKtTest {

    val gson= Gson()
    val jsonArrayStringArray = arrayOf("""[
        {
            "fid": "919a8918-8189-11e7-9f08-00163e0cb30c",
            "fclient": null,
            "fbooth": null,
            "fjumpposition": 1,
            "ffunctiontype": 0,
            "fproductid": null,
            "forder": 0,
            "fname": null,
            "furl": "www.baidu.com",
            "fimgpath": "img0/M00/05/9D/ChpbMFmSn6eEamI5AAAAAIlBwUQ075.jpg",
            "feffect": 0,
            "fbegintime": null,
            "fendtime": null,
            "fcreatorid": null,
            "fcreatetime": null,
            "fupdateuserid": null,
            "fupdatetime": null,
            "fdescrpition": null,
            "fkeyarea": null,
            "farea": null,
            "fdwelltime": 0,
            "fsliptime": 0
        },
        {
            "fid": "03e0867e-7e5c-11e7-9f08-00163e0cb30c",
            "fclient": null,
            "fbooth": null,
            "fjumpposition": 1,
            "ffunctiontype": 0,
            "fproductid": null,
            "forder": 0,
            "fname": null,
            "furl": "",
            "fimgpath": "img0/M00/05/8F/ChpbMFmNSryEeKYnAAAAADcdJRc354.jpg",
            "feffect": 0,
            "fbegintime": null,
            "fendtime": null,
            "fcreatorid": null,
            "fcreatetime": null,
            "fupdateuserid": null,
            "fupdatetime": null,
            "fdescrpition": null,
            "fkeyarea": null,
            "farea": null,
            "fdwelltime": 0,
            "fsliptime": 0
        },
        [
            {
                "fdwelltime": 5,
                "fsliptime": 0.3
            }
        ]
    ]""","""
        [
    {
      "fid": "414b83e6-eece-11e8-9463-00163e0cb30c",
      "fimgpath": "img0/M00/0C/DB/ChpbMFv3cTKEAhMaAAAAAD7RexY296.jpg"
    },
    {
      "fid": "c9bb824e-c394-11e8-9463-00163e0cb30c",
      "fimgpath": "img0/M00/0C/93/ChpbMFvbuDyEDHAyAAAAAMUFSak005.jpg"
    },
    [
      {
        "fdwelltime": 5,
        "fsliptime": 0.3
      }
    ]
  ]
    ""","""
[
    {
      "distTypeId": "55f40fa5-b6b4-4dcf-a963-52a57f53a71e",
      "distTypeName": "มแี ผง"
    },
    "..."
  ]
    """.trimIndent())
    @Test
    fun numberOf() {
        val s = "I am a %s boy yes you yes you yes !"
        s.numberOf("%s").should.be.equal(1)
        s.numberOf("yes").should.be.equal(3)
    }

    @Test
    fun arrayOfNullTest() {
        val count = 5
        val array = arrayOfNulls<String>(count)
        array.fill("Seal")
        array.forEach {
            it.should.be.equal("Seal")
        }
    }

    @Test
    fun onlyHasOneElementRecursive() {

        jsonArrayStringArray.forEach { jsonArrayString->

            val jsonArray =gson.fromJson<JsonArray>(jsonArrayString,JsonArray::class.java)

            jsonArray.onlyHasOneElementRecursive().should.be.`false`
        }
    }

    @Test
    fun onlyHasOneObjectElementRecursive() {

        jsonArrayStringArray.forEach { jsonArrayString->

            val jsonArray =gson.fromJson<JsonArray>(jsonArrayString,JsonArray::class.java)

            jsonArray.onlyHasOneObjectElementRecursive().should.be.`false`
        }
    }

    @Test
    fun onlyHasOneSubArrayAndAllAreObjectElementRecursive() {

        jsonArrayStringArray.forEach { jsonArrayString->

            val jsonArray =gson.fromJson<JsonArray>(jsonArrayString,JsonArray::class.java)

            jsonArray.onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive().should.be.`false`
        }

        val subArrayString = """[
    [
      {
        "id": 1,
        "question": "This is test question?",
        "answer": "Ok i understand.",
        "created": "2019-03-28T15:37:06+05:30",
        "created_by": 0,
        "modified": "2019-03-28T15:37:06+05:30",
        "modified_by": 0,
        "active": 1,
        "is_del": 0
      },
      {
        "id": 2,
        "question": "What is soulmate?",
        "answer": "answer",
        "created": "2019-03-28T15:41:52+05:30",
        "created_by": 0,
        "modified": "2019-03-28T15:41:52+05:30",
        "modified_by": 0,
        "active": 1,
        "is_del": 0
      }
    ]
  ]
    """.trimIndent()
        gson.fromJson<JsonArray>(
                subArrayString,
                JsonArray::class.java
        ).onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive().should.be.`true`
    }

    @Test
    fun containsAnyOf() {
        val s = "this is a awesome project !"
        s.containsAnyOf(listOf("is", "true")).should.be.`true`
        s.containsAnyOf(listOf("bad", "test")).should.be.`false`
    }

    @Test
    fun  allChildrenAreEmptyArray() {
        val emptyArray1 = """[
            []
            ]
        """

        val emptyArray2 = """[
            [[]]
            ]
        """

        val emptyArray3 = """[
            [],[]
            ]
        """

        val nonEmptyArray4 = """[
            [{"name":"tom"}],[]
            ]
        """

        gson.fromJson<JsonArray>(emptyArray1, JsonArray::class.java).allChildrenAreEmptyArray().should.be.`true`
        gson.fromJson<JsonArray>(emptyArray2, JsonArray::class.java).allChildrenAreEmptyArray().should.be.`true`
        gson.fromJson<JsonArray>(emptyArray3, JsonArray::class.java).allChildrenAreEmptyArray().should.be.`true`
        gson.fromJson<JsonArray>(nonEmptyArray4, JsonArray::class.java).allChildrenAreEmptyArray().should.be.`false`
    }
}
