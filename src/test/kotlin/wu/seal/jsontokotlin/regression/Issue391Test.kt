package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue391Test :BaseTest(){


    @Test
    fun testIssue391() {
       val json = """
           [
                {
                    "url": null,
                    "label": "&laquo; Previous",
                    "active": false
                },
                {
                    "url": "http://localhost:8000/api/active-announcements?page=1",
                    "label": "1",
                    "active": true
                },
                {
                    "url": "http://localhost:8000/api/active-announcements?page=2",
                    "label": "2",
                    "active": false
                },
                {
                    "url": "http://localhost:8000/api/active-announcements?page=3",
                    "label": "3",
                    "active": false
                },
      ]
       """.trimIndent()
        val expected = """
            class Test : ArrayList<TestItem>()

            @JsonClass(generateAdapter = true)
            data class TestItem(
                @Json(name = "active")
                val active: Boolean = false,
                @Json(name = "label")
                val label: String = "",
                @Json(name = "url")
                val url: String? = null
            )
        """.trimIndent()
        TestConfig.targetJsonConvertLib = TargetJsonConverter.MoshiCodeGen
        TestConfig.isCommentOff = true
        TestConfig.isNestedClassModel = false
        TestConfig.defaultValueStrategy = DefaultValueStrategy.AllowNull
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        json.generateKotlinClassCode("Test").should.equal(expected)
    }
}