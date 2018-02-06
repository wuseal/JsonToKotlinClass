package wu.seal.jsontokotlin.supporter

import org.junit.Assert.assertFalse

/**
 *
 * Created by Seal.Wu on 2017/10/19.
 */
class FastjsonSupporterTest {
    @org.junit.Test
    fun getJsonLibSupportPropertyBlockString() {

        wu.seal.jsontokotlin.isTestModel = true

        val propertyStringBlock1 = FastjsonSupporter.getJsonLibSupportPropertyBlockString("ABC", "String")
        val propertyStringBlock2 = FastjsonSupporter.getJsonLibSupportPropertyBlockString("isABC", "String")
        val propertyStringBlock3 = FastjsonSupporter.getJsonLibSupportPropertyBlockString("isisisABC", "String")
        val propertyStringBlock4 = FastjsonSupporter.getJsonLibSupportPropertyBlockString("ISABC", "String")
        val propertyStringBlock5 = FastjsonSupporter.getJsonLibSupportPropertyBlockString("isISABC", "String")

        println(propertyStringBlock1)
        println(propertyStringBlock2)
        println(propertyStringBlock3)
        println(propertyStringBlock4)
        println(propertyStringBlock5)

        assertFalse(propertyStringBlock1.startsWith("is"))
        assertFalse(propertyStringBlock2.startsWith("is"))
        assertFalse(propertyStringBlock3.startsWith("is"))
        assertFalse(propertyStringBlock4.startsWith("is"))
        assertFalse(propertyStringBlock5.startsWith("is"))

    }

}