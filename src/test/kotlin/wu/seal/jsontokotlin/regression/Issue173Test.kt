package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue173Test {

    private val testJson = "{\n" +
            "  \"employees\": [\n" +
            "    {\n" +
            "      \"lastName\": \"tom\"\n" +
            "    },\n" +
            "    {   \n" +
            "      \"lastName\": null\n" +
            "    },\n" +
            "    {  \n" +
            "      \"lastName\": null\n" +
            "    }\n" +
            "  ]\n" +
            "}"


    private val expected = "data class A(\n" +
            "    @SerializedName(\"employees\")\n" +
            "    val employees: List<Employee> = listOf()\n" +
            ") {\n" +
            "    data class Employee(\n" +
            "        @SerializedName(\"lastName\")\n" +
            "        val lastName: String = \"\" // tom\n" +
            "    )\n" +
            "}"




    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    /**
     * test issue #700 of Github Project issue
     */
    @Test
    fun testIssue173() {
        val generated = KotlinClassCodeMaker(
                KotlinClassMaker("A", testJson).makeKotlinClass()).makeKotlinClassCode()
        generated.trim().should.be.equal(expected)
    }
}