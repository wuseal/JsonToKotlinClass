package wu.seal.jsontokotlin.supporter

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.getIndent

/**
 *
 * Created by Seal.Wu on 2017/11/1.
 */
class CustomJsonLibSupporterTest {
    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @org.junit.Test
    fun testAnnotationImportClass() {

        println(ConfigManager.customAnnotaionImportClassString)
    }

    @org.junit.Test
    fun getJsonLibSupportPropertyBlockString() {

        val rawPropertyName = "seal_ wu"
        val propertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, "String")

        println(propertyBlockString)

        assert(propertyBlockString.contains(rawPropertyName))

    }

    @org.junit.Test
    fun getJsonLibSupportPropertyWithAnnotationWithoutPercentSBlockString() {

        val rawPropertyName = "seal_ wu"
        val s = "@yesAnnotation()"
        TestConfig.customPropertyAnnotationFormatString = s

        val propertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, "String")

        println(propertyBlockString)

        val expectedBlockString = """${getIndent()}@yesAnnotation()
${getIndent()}val seal_ wu: String = """""

        propertyBlockString.should.be.equal(expectedBlockString)
        assert(propertyBlockString.contains(CustomJsonLibSupporter.getPropertyAnnotationString(rawPropertyName)))

    }

    @Test
    fun getPropertyAnnotationStringWithDoubleFillTest() {
        val s = "yes I am a %s  %s"
        TestConfig.customPropertyAnnotationFormatString = s
        val result = CustomJsonLibSupporter.getPropertyAnnotationString("Man")
        result.should.be.equal("yes I am a Man  Man")
    }

    @Test
    fun getClassAnnotationStringWithDoubleFillTest() {
        val s = "yes I am a %s  %s  Class"
        TestConfig.customClassAnnotationFormatString = s
        val result = CustomJsonLibSupporter.getClassAnnotationBlockString("Man")
        result.should.be.equal("yes I am a Man  Man  Class")
    }


}