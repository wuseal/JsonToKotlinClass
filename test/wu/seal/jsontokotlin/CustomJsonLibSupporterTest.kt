package wu.seal.jsontokotlin

import org.junit.Test
import wu.seal.jsontokotlin.supporter.CustomJsonLibSupporter

/**
 * Created by Seal.Wu on 2017/11/1.
 */
class CustomJsonLibSupporterTest {

    @Test
    fun testAnnotationImportClass() {
        isTestModel = true
        println(ConfigManager.customAnnotaionImportClassString)
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