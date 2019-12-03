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

        // PropertyTypeStrategy.Nullable
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
    fun setPropertyTypeStrategyNotNullable() {

        // PropertyTypeStrategy.NotNullable
        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any // null
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.NotNullable)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setDefaultValueStrategyAllowNull() {


        val input = """
            {"name": "john", "company": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company: Any? = null, // null
                val name: String = "" // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setDefaultValueStrategy(DefaultValueStrategy.AllowNull)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setDefaultValueStrategyAvoidNull() {


        val input = """
            {"name": "john", "company": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company: Any? = Any(), // null
                val name: String = "" // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setDefaultValueStrategy(DefaultValueStrategy.AvoidNull)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setDefaultValueStrategyNone() {


        val input = """
            {"name": "john", "company": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company: Any?, // null
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setDefaultValueStrategy(DefaultValueStrategy.None)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibGson() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                @SerializedName("company")
                val company: String, // ABC Ltd
                @SerializedName("name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.Gson)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibMoshi() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                @Json(name = "company")
                val company: String, // ABC Ltd
                @Json(name = "name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.MoShi)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibMoshiCodeGen() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            @JsonClass(generateAdapter = true)
            data class User(
                @Json(name = "company")
                val company: String, // ABC Ltd
                @Json(name = "name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.MoshiCodeGen)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibFastJson() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                @JSONField(name = "company")
                val company: String, // ABC Ltd
                @JSONField(name = "name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.FastJson)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibJackson() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                @JsonProperty("company")
                val company: String, // ABC Ltd
                @JsonProperty("name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.Jackson)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibLoganSquare() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            @JsonObject
            data class User(
                @JsonField(name = arrayOf("company"))
                val company: String, // ABC Ltd
                @JsonField(name = arrayOf("name"))
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.LoganSquare)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibSerializable() {

        val input = """
            {"name": "john", "company": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            @Serializable
            data class User(
                @Optional
                @SerialName("company")
                val company: String, // ABC Ltd
                @Optional
                @SerialName("name")
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.Serilizable)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibNoneWithCamelCase() {

        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val companyName: String, // ABC Ltd
                val userName: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.NoneWithCamelCase)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setAnnotationLibNone() {

        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company_name: String, // ABC Ltd
                val user_name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.None)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCustomAnnotation() {

        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            @Serializable
            data class User(
                @Optional
                @SerialName("company_name")
                val companyName: String, // ABC Ltd
                @Optional
                @SerialName("user_name")
                val userName: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setCustomAnnotation(
                        "import kotlinx.serialization.SerialName\n" +
                                "import kotlinx.serialization.Serializable" + "\n" + "import kotlinx.serialization.Optional",
                        "@Serializable",
                        "@Optional\n@SerialName(\"%s\")"
                )
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCommentEnabled() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setComment(true)
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCommentDisabled() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setComment(false)
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setOrderByAlphabeticEnabled() {
        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company_name: String, // ABC Ltd
                val user_name: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setOrderByAlphabetic(true)
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setOrderByAlphabeticDisabled() {
        val input = """
            {"user_name": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val user_name: String, // john
                val company_name: String // ABC Ltd
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setOrderByAlphabetic(false)
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setInnerClassModelEnabled() {
        val input = """
            {
                "glossary":{
                    "title":"example glossary",
                    "GlossDiv":{
                        "title":"S",
                        "GlossList":{
                            "GlossEntry":{
                                "ID":"SGML",
                                "SortAs":"SGML",
                                "GlossTerm":"Standard Generalized Markup Language",
                                "Acronym":"SGML",
                                "Abbrev":"ISO 8879:1986",
                                "GlossDef":{
                                    "para":"A meta-markup language, used to create markup languages such as DocBook.",
                                    "GlossSeeAlso":[
                                        "GML",
                                        "XML"
                                    ]
                                },
                                "GlossSee":"markup"
                            }
                        }
                    }
                }
            }
        """.trimIndent()

        val expectedOutput = """
            data class GlossResponse(
                val glossary: Glossary
            ) {
                data class Glossary(
                    val title: String, // example glossary
                    val GlossDiv: GlossDiv
                ) {
                    data class GlossDiv(
                        val title: String, // S
                        val GlossList: GlossList
                    ) {
                        data class GlossList(
                            val GlossEntry: GlossEntry
                        ) {
                            data class GlossEntry(
                                val ID: String, // SGML
                                val SortAs: String, // SGML
                                val GlossTerm: String, // Standard Generalized Markup Language
                                val Acronym: String, // SGML
                                val Abbrev: String, // ISO 8879:1986
                                val GlossDef: GlossDef,
                                val GlossSee: String // markup
                            ) {
                                data class GlossDef(
                                    val para: String, // A meta-markup language, used to create markup languages such as DocBook.
                                    val GlossSeeAlso: List<String>
                                )
                            }
                        }
                    }
                }
            }
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setOrderByAlphabetic(false)
                .setInnerClassModel(true)
                .build(input, "GlossResponse")
        actualOutput.should.be.equal(expectedOutput)
    }


    @Test
    fun setInnerClassModelDisabled() {
        val input = """
            {
                "glossary":{
                    "title":"example glossary",
                    "GlossDiv":{
                        "title":"S",
                        "GlossList":{
                            "GlossEntry":{
                                "ID":"SGML",
                                "SortAs":"SGML",
                                "GlossTerm":"Standard Generalized Markup Language",
                                "Acronym":"SGML",
                                "Abbrev":"ISO 8879:1986",
                                "GlossDef":{
                                    "para":"A meta-markup language, used to create markup languages such as DocBook.",
                                    "GlossSeeAlso":[
                                        "GML",
                                        "XML"
                                    ]
                                },
                                "GlossSee":"markup"
                            }
                        }
                    }
                }
            }
        """.trimIndent()

        val expectedOutput = """
            data class GlossResponse(
                val glossary: Glossary
            )

            data class Glossary(
                val title: String, // example glossary
                val GlossDiv: GlossDiv
            )

            data class GlossDiv(
                val title: String, // S
                val GlossList: GlossList
            )

            data class GlossList(
                val GlossEntry: GlossEntry
            )

            data class GlossEntry(
                val ID: String, // SGML
                val SortAs: String, // SGML
                val GlossTerm: String, // Standard Generalized Markup Language
                val Acronym: String, // SGML
                val Abbrev: String, // ISO 8879:1986
                val GlossDef: GlossDef,
                val GlossSee: String // markup
            )

            data class GlossDef(
                val para: String, // A meta-markup language, used to create markup languages such as DocBook.
                val GlossSeeAlso: List<String>
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setOrderByAlphabetic(false)
                .setInnerClassModel(false)
                .build(input, "GlossResponse")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setMapTypeEnabled() {
        val input = """
            {a:{1:1,2:2}}
        """.trimIndent()

        val expectedOutput = """
            data class Model(
                val a: Map<Int,Int>
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setMapType(true)
                .build(input, "Model")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setCreateAnnotationOnlyWhenNeeded() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                @SerializedName("company_name")
                val companyName: String, // ABC Ltd
                val username: String // john
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.Gson)
                .setCreateAnnotationOnlyWhenNeeded(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
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