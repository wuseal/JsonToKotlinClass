package wu.seal.jsontokotlin.supporter

import org.junit.*
import org.junit.Test

import wu.seal.jsontokotlin.supporter.MoShiSupporter

/**
 * 
 * Created by Seal.Wu on 2017/11/1.
 */
class MoShiSupporterTest {
    @org.junit.Before
    fun setUp() {
        wu.seal.jsontokotlin.isTestModel = true
    }

    @org.junit.Test
    fun getAnnotationImportClassString() {
        assert(MoShiSupporter.annotationImportClassString.isNotEmpty())
        assert(MoShiSupporter.annotationImportClassString.split("\n").size ==1)
    }

    @org.junit.Test
    fun getJsonLibSupportPropertyBlockString() {
        val rawPropertyName = "seal is **() good_man "
        val type = "Boy"
        val block = MoShiSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, type)
        assert(block.contains(rawPropertyName))
        assert(block.contains(type))
    }

}