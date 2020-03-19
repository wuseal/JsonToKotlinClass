package wu.seal.jsontokotlin.model.codeelements

import com.winterbe.expekt.should
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Created by Seal.Wu on 2017/12/18.
 */
class KPropertyNameTest {
    @org.junit.Test
    fun getName() {
        val originNmae = """
                !@#$ -_32322 3 32%N^&*(-a)_+-=m123-35 e43{}|[]\\;':1",./<>?/*-+`
                """
        val resultName = KPropertyName.getName(originNmae)

        assertTrue(resultName.startsWith("n"))
        assertTrue(resultName.endsWith("1"))
        assertTrue(resultName.contains("A"))
        assertTrue(resultName.contains("M"))
        assertTrue(resultName.contains("E"))


        val rawName = "#$@#4324324$$#@324"
        val legalName = KPropertyName.getName(rawName)
        assertTrue(legalName.isNotEmpty())
        assertTrue(legalName.startsWith("x"))

    }

    @Test
    fun makePropertyName() {
        val originNmae = """
                !@#$ -_32322 3 32%N^&*(-a)_+-=m123-35 e43{}|[]\\;':1",./<>?/*-+`
                """
        val resultName = KPropertyName.makePropertyName(originNmae)

        assertTrue(originNmae == resultName)


    }

    @Test
    fun makePropertyName1() {
        val originNmae = """
                !@#$ -_32322 3 32%N^&*(-a)_+-=m123-35 e43{}|[]\\;':1",./<>?/*-+`
                """
        val resultNameNotToBeLegal = KPropertyName.makePropertyName(originNmae, false)

        assertTrue(originNmae == resultNameNotToBeLegal)

        val resultNameToBeLegal = KPropertyName.makePropertyName(originNmae, true)

        assertTrue(originNmae != resultNameToBeLegal)
        assertTrue(resultNameToBeLegal.startsWith("n"))
        assertTrue(resultNameToBeLegal.endsWith("1"))
        assertTrue(resultNameToBeLegal.contains("A"))
        assertTrue(resultNameToBeLegal.contains("M"))
        assertTrue(resultNameToBeLegal.contains("E"))

        val rawName = "#$@#4324324$$#@324"
        val legalName = KPropertyName.getName(rawName)
        assertTrue(legalName.isNotEmpty())
        assertTrue(legalName.startsWith("x"))
    }

    @Test
    fun makePropertyNameWithCamelCase() {

        val rawNameWithSpace = "abc abc"
        val rawNameWithUnderScore = "abc_abc"
        val rawNameWithHyphen = "abc-abc"
        val resultCamelCaseNameWithSpace = KPropertyName.makePropertyName(rawNameWithSpace, true)
        val resultCamelCaseNameUnderScore = KPropertyName.makePropertyName(rawNameWithUnderScore, true)
        val resultCamelCaseNameWithMiddleScore = KPropertyName.makePropertyName(rawNameWithHyphen, true)
        resultCamelCaseNameWithSpace.should.be.equal("abcAbc")
        resultCamelCaseNameUnderScore.should.be.equal("abcAbc")
        resultCamelCaseNameWithMiddleScore.should.be.equal("abcAbc")
    }

    /**
     * Test Property name camle case format if OK when there two colon inside string
     */
    @Test
    fun testPropertyNameCameCaseFormatWithTwoColon() {
        KPropertyName.getName("hello:baby:come").should.be.equal("helloBabyCome")
    }
}