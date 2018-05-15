package wu.seal.jsontokotlin.supporter

import org.junit.After
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 *
 * Created by Seal.Wu on 2017/11/1.
 */
class LoganSquareSupporterTest {
    @Before
    fun setUp() {
        isTestModel = true
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getClassAnnotation() {
        assert(LoganSquareSupporter.getClassAnnotationBlockString("TestClass").isNotEmpty())
    }

    @Test
    fun getAnnotationImportClassString() {
        assert(LoganSquareSupporter.annotationImportClassString.isNotEmpty())
        assert(LoganSquareSupporter.annotationImportClassString.split("\n").size == 2)
    }

    @Test
    fun getJsonLibSupportPropertyBlockString() {
        val rawPropertyName = "seal is **() good_man "
        val type = "Boy"
        val block = LoganSquareSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, type)
        assert(block.contains(rawPropertyName))
        assert(block.contains(type))
    }

}
