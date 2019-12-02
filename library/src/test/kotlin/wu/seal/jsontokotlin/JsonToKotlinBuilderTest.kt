package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Test

class JsonToKotlinBuilderTest {

    @Test
    fun build() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder().build(input, "User")
        actualOutput.should.be.equal(expectedOutput)

    }

    @Test
    fun setPropertiesVar() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                var name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertiesVar(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyAutoDeterMineNullableOrNot() {

        // Auto Determine
        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any? // null
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyNullable() {

        // Auto Determine
        val input = """
            {"name": "john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String? // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.Nullable)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setDefaultValueStrategy() {
    }

    @Test
    fun setAnnotationLib() {
    }

    @Test
    fun setCustomAnnotation() {
    }

    @Test
    fun setComment() {
    }

    @Test
    fun setOrderByAlphabetic() {
    }

    @Test
    fun setInnerClassModel() {
    }

    @Test
    fun setMapType() {
    }

    @Test
    fun setCreateAnnotationOnlyWhenNeeded() {
    }

    @Test
    fun setIndent() {
    }

    @Test
    fun setParentClassTemplate() {
    }

    @Test
    fun setKeepAnnotationOnClass() {
    }

    @Test
    fun setKeepAnnotationOnClassAndroidX() {
    }

    @Test
    fun setKeepAnnotationAndPropertyInSameLine() {
    }

    @Test
    fun setParcelableSupport() {
    }

    @Test
    fun setPropertyPrefix() {
    }

    @Test
    fun setPropertySuffix() {
    }

    @Test
    fun setClassSuffix() {
    }

    @Test
    fun setForceInitDefaultValueWithOriginJsonValue() {
    }

    @Test
    fun setForcePrimitiveTypeNonNullable() {
    }


}