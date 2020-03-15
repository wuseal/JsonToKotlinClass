package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue121Test {

    private val json = """{"data": [
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
}"""

    private val expected = """data class Test(
    @SerializedName("data")
    val `data`: List<List<Data>> = listOf()
) {
    data class Data(
        @SerializedName("active")
        val active: Int = 0, // 1
        @SerializedName("answer")
        val answer: String = "", // Ok i understand.
        @SerializedName("created")
        val created: String = "", // 2019-03-28T15:37:06+05:30
        @SerializedName("created_by")
        val createdBy: Int = 0, // 0
        @SerializedName("id")
        val id: Int = 0, // 1
        @SerializedName("is_del")
        val isDel: Int = 0, // 0
        @SerializedName("modified")
        val modified: String = "", // 2019-03-28T15:37:06+05:30
        @SerializedName("modified_by")
        val modifiedBy: Int = 0, // 0
        @SerializedName("question")
        val question: String = "" // This is test question?
    )
}"""

    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    /**
     * test issue #121 of Github Project issue
     */
    @Test
    fun testIssue121() {
        val result = KotlinClassCodeMaker(
                KotlinClassMaker("Test", json).makeKotlinClass()).makeKotlinClassCode()
        result.trim().should.be.equal(expected)
    }
}