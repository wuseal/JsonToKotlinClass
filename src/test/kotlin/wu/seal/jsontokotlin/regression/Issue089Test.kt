package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue089Test {

    private val json = """{
  "mime_type": "image/jpeg",
  "file_name": "136149.jpg",
  "source_type": "chk",
  "source_id": 3000193,
  "year_month": "201810",
  "day": "31",
  "type_document_id": 11,
  "usr_id": 55572,
  "file_desc": "текст примечания",
  "bytes": "/9j/4AAQSkZ...Nd/VJ/Ef",
  "description": {
    "cmis:objectTypeId": "D:esp:act",
    "esp:doc_name": "текст примечания",
    "esp:iin_bin": "101040011256",
    "esp:create_date": "2018.09.31",
    "esp:reg_num": "181000000103012/00022",
    "esp:reg_date": "2018.10.01",
    "esp:author": "Уалиева Асель Мухаметбековна"
  }
}"""

    private val expected = """data class Test(
    @SerializedName("bytes")
    val bytes: String = "", // /9j/4AAQSkZ...Nd/VJ/Ef
    @SerializedName("day")
    val day: String = "", // 31
    @SerializedName("description")
    val description: Description = Description(),
    @SerializedName("file_desc")
    val fileDesc: String = "", // текст примечания
    @SerializedName("file_name")
    val fileName: String = "", // 136149.jpg
    @SerializedName("mime_type")
    val mimeType: String = "", // image/jpeg
    @SerializedName("source_id")
    val sourceId: Int = 0, // 3000193
    @SerializedName("source_type")
    val sourceType: String = "", // chk
    @SerializedName("type_document_id")
    val typeDocumentId: Int = 0, // 11
    @SerializedName("usr_id")
    val usrId: Int = 0, // 55572
    @SerializedName("year_month")
    val yearMonth: String = "" // 201810
) {
    data class Description(
        @SerializedName("cmis:objectTypeId")
        val cmisObjectTypeId: String = "", // D:esp:act
        @SerializedName("esp:author")
        val espAuthor: String = "", // Уалиева Асель Мухаметбековна
        @SerializedName("esp:create_date")
        val espCreateDate: String = "", // 2018.09.31
        @SerializedName("esp:doc_name")
        val espDocName: String = "", // текст примечания
        @SerializedName("esp:iin_bin")
        val espIinBin: String = "", // 101040011256
        @SerializedName("esp:reg_date")
        val espRegDate: String = "", // 2018.10.01
        @SerializedName("esp:reg_num")
        val espRegNum: String = "" // 181000000103012/00022
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
     * test issue #89 of Github Project issue
     */
    @Test
    fun testIssue089() {
        val result = KotlinClassCodeMaker(KotlinClassMaker("Test", json).makeKotlinClass()).makeKotlinClassCode()
        result.trim().should.be.equal(expected)
    }
}