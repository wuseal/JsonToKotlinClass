package wu.seal.jsontokotlin.codeelements

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 *
 * Created by Seal.Wu on 2017/12/18.
 */
class KClassNameTest {

    @org.junit.Test
    fun getLegalClassName() {

        val rawClassName = """
                !@3214 12#$%n^&*(-a)_+-=m12335_e43{}|[]\\;':1",./<>?/*-+`
                """

        val legalClassName = KClassName.getLegalClassName(rawClassName)

        assertTrue(legalClassName.startsWith("N"))
        assertTrue(legalClassName == "NAM12335E431")

        val rawClassName1 = "341@!$#43214%$#@%34"
        val legalClassName1 = KClassName.getLegalClassName(rawClassName1)
        assertTrue(legalClassName1.isNotEmpty())
        assertTrue(legalClassName1 == "X3414321434")

    }

    @Test
    fun getName() {
        val rawClassName = """
                !@3214 12#$%n^&*(-a)_+-=m12335_e43{}|[]\\;':1",./<>?/*-+`
                """

        val legalClassName = KClassName.getName(rawClassName)

        assertTrue(legalClassName.startsWith("N"))
        assertTrue(legalClassName == "NAM12335E431")

        val rawClassName1 = "341@!$#43214%$#@%34"
        val legalClassName1 = KClassName.getName(rawClassName1)
        assertTrue(legalClassName1.isNotEmpty())
        assertTrue(legalClassName1 == "X3414321434")

    }

}