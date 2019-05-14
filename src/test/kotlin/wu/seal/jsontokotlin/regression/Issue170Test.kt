package wu.seal.jsontokotlin.regression

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.KotlinDataClassCodeMaker
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.test.TestConfig

class Issue170Test {

    private val testJson = "{\n" +
            "  \"employees\": [\n" +
            "    {\n" +
            "      \"firstName\": \"Bill\",\n" +
            "      \"lastName\": \"Gates\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"firstName\": null,\n" +
            "      \"lastName\": \"Bush\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"firstName\": \"Thomas\",\n" +
            "      \"lastName\": \"Carter\"\n" +
            "    }\n" +
            "  ]\n" +
            "}"


    private val expected = "data class A(\n" +
            "    @SerializedName(\"employees\")\n" +
            "    val employees: List<Employee>\n" +
            ") {\n" +
            "    data class Employee(\n" +
            "        @SerializedName(\"firstName\")\n" +
            "        val firstName: String?, // Thomas\n" +
            "        @SerializedName(\"lastName\")\n" +
            "        val lastName: String // Carter\n" +
            "    )\n" +
            "}"




    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
        TestConfig.initWithDefaultValue = false
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
    }

    /**
     * test issue #700 of Github Project issue
     */
    @Test
    fun testIssue130() {
        val generated = KotlinDataClassCodeMaker("A", testJson).makeKotlinDataClassCode()
        generated.trim().should.be.equal(expected)
    }
}