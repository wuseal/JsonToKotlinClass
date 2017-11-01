package wu.seal.jsontokotlin

import org.junit.*
import org.junit.Test

import wu.seal.jsontokotlin.supporter.LoganSquareSupporter

/**
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

    @org.junit.Test
    fun getClassAnnotation() {
        assert(LoganSquareSupporter.classAnnotation.isNotEmpty())
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
