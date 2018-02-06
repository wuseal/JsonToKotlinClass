package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager

/**
 * 
 * Created by Seal.Wu on 2017/11/1.
 */
class CustomJsonLibSupporterTest {

    @org.junit.Test
    fun testAnnotationImportClass() {
        wu.seal.jsontokotlin.isTestModel = true
        println(wu.seal.jsontokotlin.ConfigManager.customAnnotaionImportClassString)
    }

    @org.junit.Test
    fun getJsonLibSupportPropertyBlockString() {

        wu.seal.jsontokotlin.isTestModel =true
        val rawPropertyName = "seal_ wu"
        val propertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, "String")

        println(propertyBlockString)

        assert(propertyBlockString.contains(String.format(ConfigManager.customAnnotaionFormatString,rawPropertyName)))

    }

}