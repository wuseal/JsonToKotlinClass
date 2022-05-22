package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.generateKotlinClassCode
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import wu.seal.jsontokotlin.model.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.BaseTest

class Issue379Test :BaseTest() {

    @Test
    fun testIssue379() {
       val json = """
           [
              {
                 "weight":{
                    "imperial":"9 - 14",
                    "metric":"4 - 6"
                 },
                 "image":{
                  
                 }
              },
              {
                 "weight":{
                    "imperial":"12 - 18",
                    "metric":"5 - 8"
                 },
                 "image":{
                    "id":"0SxW2SQ_S",
                    "width":800,
                    "height":1203,
                    "url":"https://smth.jpg"
                 }
              }
           ]
       """.trimIndent()
        val expected = """
            class Test : ArrayList<TestItem>(){
                data class TestItem(
                    val image: Image?,
                    val weight: Weight
                ) {
                    data class Image(
                        val height: Int?,
                        val id: String?,
                        val url: String?,
                        val width: Int?
                    )
                
                    data class Weight(
                        val imperial: String,
                        val metric: String
                    )
                }
            }
        """.trimIndent()
        TestConfig.isCommentOff = true
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.targetJsonConvertLib = TargetJsonConverter.None
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        json.generateKotlinClassCode("Test").should.equal(expected)
    }
    @Test
    fun testIssue379Case2() {
        val json = """
            {
              "employees": [
                {
                  "team": "Point of Sale",
                  "employee_type": "FULL_TIME"
                },
                {
                  "employee_type": "PART_TIME"
                }
              ]
            }
       """.trimIndent()

        val expected = """
            data class Test(
                @SerializedName("employees")
                val employees: List<Employee> = listOf()
            ) {
                data class Employee(
                    @SerializedName("employee_type")
                    val employeeType: String = "",
                    @SerializedName("team")
                    val team: String? = ""
                )
            }
        """.trimIndent()

        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
        TestConfig.isCommentOff = true
        json.generateKotlinClassCode("Test").should.equal(expected)
    }
}