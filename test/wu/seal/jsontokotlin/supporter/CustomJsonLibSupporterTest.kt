package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 * 
 * Created by Seal.Wu on 2017/11/1.
 */
class CustomJsonLibSupporterTest {

    @org.junit.Test
    fun testAnnotationImportClass() {
        isTestModel = true
        println(wu.seal.jsontokotlin.ConfigManager.customAnnotaionImportClassString)
    }

    @org.junit.Test
    fun getJsonLibSupportPropertyBlockString() {

        isTestModel =true
        val rawPropertyName = "seal_ wu"
        val propertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, "String")

        println(propertyBlockString)

        assert(propertyBlockString.contains(String.format(ConfigManager.customAnnotaionFormatString,rawPropertyName)))

    }

}