package wu.seal.jsontokotlin

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class KotlinMakerTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    @Test
    fun makeKotlinData() {
        val json1 = """{ "progr ammers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "aut_hors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

        val json2 = """ {"123menu": {
                "i d": [1,23,34],
                "value":[],
                "popup": {
                "m#@!$#%$#^%*^&(*)*(_)+{|}{:enu_item": [
                {"value": "New", "onclick": "CreateNewDoc()"},
                {"value": "Open", "onclick": "OpenDoc()"},
                {"value": "Close", "onclick": "CloseDoc()"}
                ]}
                }}"""

        println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")

        TestConfig.targetJsonConvertLib = TargetJsonConverter.Jackson
        TestConfig.isCommentOff = true
        TestConfig.isPropertiesVar = true
        TestConfig.isPropertyNullable = false

        println("===========================================Change to Jackson json lib support========================================= ")

        println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")


        TestConfig.targetJsonConvertLib = TargetJsonConverter.FastJson
        TestConfig.isCommentOff = true
        TestConfig.isPropertiesVar = true
        TestConfig.isPropertyNullable = false

        println("===========================================Change to FastJson json lib support========================================= ")

        println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")



        TestConfig.targetJsonConvertLib = TargetJsonConverter.Gson
        TestConfig.isCommentOff = false
        TestConfig.isPropertiesVar = false
        TestConfig.isPropertyNullable = true

        println("===========================================Change to Gson json lib support========================================= ")

        println("json1 ====>\n${KotlinMaker("Class1", json1).makeKotlinData()}")
        println("json2 ====>\n${KotlinMaker("Class2", json2).makeKotlinData()}")
    }

}