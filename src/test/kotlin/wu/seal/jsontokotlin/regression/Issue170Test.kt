package wu.seal.jsontokotlin.regression


import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
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


    private val expected = """data class A(
    @SerializedName("employees")
    val employees: List<Employee>
) {
    data class Employee(
        @SerializedName("firstName")
        val firstName: String?, // Bill
        @SerializedName("lastName")
        val lastName: String // Gates
    )
}"""




    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
        TestConfig.defaultValueStrategy = DefaultValueStrategy.None
        TestConfig.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
    }

    /**
     * test issue #700 of Github Project issue
     */
    @Test
    fun testIssue130() {
        val generated = KotlinClassCodeMaker(
                KotlinClassMaker("A", testJson).makeKotlinClass()).makeKotlinClassCode()
        generated.trim().should.be.equal(expected)
    }
}