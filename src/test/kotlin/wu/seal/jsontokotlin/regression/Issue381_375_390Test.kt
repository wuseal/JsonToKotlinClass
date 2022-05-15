package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue381_375_390Test : BaseTest() {

    @Test
    fun testIssue375() {
        TestConfig.isNestedClassModel = false
        val json = """
           {
               "glossary":{
                   "title":"example glossary",
                   "GlossDiv":{
                       "title":"S"
                   }
               },
               "GlossDiv":{
                   "title":"S"
               }
           }
       """.trimIndent()

        val expected = """
            data class Test(
                @SerializedName("GlossDiv")
                val glossDiv: GlossDiv = GlossDiv(),
                @SerializedName("glossary")
                val glossary: Glossary = Glossary()
            )

            data class GlossDiv(
                @SerializedName("title")
                val title: String = "" // S
            )

            data class Glossary(
                @SerializedName("GlossDiv")
                val glossDiv: GlossDiv = GlossDiv(),
                @SerializedName("title")
                val title: String = "" // example glossary
            )
        """.trimIndent()

        json.generateKotlinClassCode("Test").should.equal(expected)
    }

    @Test
    fun testIssue390() {
        val json = """
            {
                "data1":{
                    "name":"name1",
                    "value":"value1"
                },
                "data2":{
                    "name":"name2",
                    "value":"value2"
                }
            }
        """.trimIndent()

        val expected = """
            data class Test(
                @SerializedName("data1")
                val data1: Data1 = Data1(),
                @SerializedName("data2")
                val data2: Data1 = Data1()
            )

            data class Data1(
                @SerializedName("name")
                val name: String = "",
                @SerializedName("value")
                val value: String = ""
            )
        """.trimIndent()
        TestConfig.isCommentOff = true
        TestConfig.isNestedClassModel = false
        json.generateKotlinClassCode("Test").should.equal(expected)
    }
}