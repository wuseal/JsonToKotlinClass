package wu.seal.jsontokotlin

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

class JsonToKotlinBuilderTest {


    @Test
    fun build() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertiesVar() {

        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                var name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertiesVar(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyAutoDeterMineNullableOrNot() {


        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any?
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyNullable() {


        val input = """
            {"name": "john"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: String?
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.Nullable)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyTypeStrategyNotNullable() {


        val input = """
            {"name": null}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val name: Any
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
                val company: Any? = null,
                val name: String = ""
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
                val company: Any? = Any(),
                val name: String = ""
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
                val company: Any?,
                val name: String
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
            
            import com.google.gson.annotations.SerializedName
            
            data class User(
                @SerializedName("company")
                val company: String,
                @SerializedName("name")
                val name: String
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
            
            import com.squareup.moshi.Json
            
            data class User(
                @Json(name = "company")
                val company: String,
                @Json(name = "name")
                val name: String
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
            
            import com.squareup.moshi.Json
            import com.squareup.moshi.JsonClass
            
            @JsonClass(generateAdapter = true)
            data class User(
                @Json(name = "company")
                val company: String,
                @Json(name = "name")
                val name: String
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
            
            import com.alibaba.fastjson.annotation.JSONField
            
            data class User(
                @JSONField(name = "company")
                val company: String,
                @JSONField(name = "name")
                val name: String
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
            
            import com.fasterxml.jackson.annotation.JsonProperty
            
            data class User(
                @JsonProperty("company")
                val company: String,
                @JsonProperty("name")
                val name: String
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
            
            import com.bluelinelabs.logansquare.annotation.JsonField
            import com.bluelinelabs.logansquare.annotation.JsonObject
            
            @JsonObject
            data class User(
                @JsonField(name = arrayOf("company"))
                val company: String,
                @JsonField(name = arrayOf("name"))
                val name: String
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
            
            import kotlinx.serialization.SerialName
            import kotlinx.serialization.Serializable
            import kotlinx.serialization.Optional
            
            @Serializable
            data class User(
                @Optional
                @SerialName("company")
                val company: String,
                @Optional
                @SerialName("name")
                val name: String
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
                val companyName: String,
                val userName: String
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
                val company_name: String,
                val user_name: String
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
            
            import kotlinx.serialization.SerialName
            import kotlinx.serialization.Serializable
            import kotlinx.serialization.Optional
            
            @Serializable
            data class User(
                @Optional
                @SerialName("company_name")
                val companyName: String,
                @Optional
                @SerialName("user_name")
                val userName: String
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
                val company_name: String,
                val user_name: String
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
                val user_name: String,
                val company_name: String
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
                    val title: String,
                    val GlossDiv: GlossDiv
                ) {
                    data class GlossDiv(
                        val title: String,
                        val GlossList: GlossList
                    ) {
                        data class GlossList(
                            val GlossEntry: GlossEntry
                        ) {
                            data class GlossEntry(
                                val ID: String,
                                val SortAs: String,
                                val GlossTerm: String,
                                val Acronym: String,
                                val Abbrev: String,
                                val GlossDef: GlossDef,
                                val GlossSee: String
                            ) {
                                data class GlossDef(
                                    val para: String,
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
                val title: String,
                val GlossDiv: GlossDiv
            )

            data class GlossDiv(
                val title: String,
                val GlossList: GlossList
            )

            data class GlossList(
                val GlossEntry: GlossEntry
            )

            data class GlossEntry(
                val ID: String,
                val SortAs: String,
                val GlossTerm: String,
                val Acronym: String,
                val Abbrev: String,
                val GlossDef: GlossDef,
                val GlossSee: String
            )

            data class GlossDef(
                val para: String,
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
            
            import com.google.gson.annotations.SerializedName
            
            data class User(
                @SerializedName("company_name")
                val companyName: String,
                val username: String
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
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                      val company_name: String,
                      val username: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setIndent(10)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setParentClassTemplate() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            import android.os.Parcelable
            
            data class User(
                val company_name: String,
                val username: String
            ) : Parcelable
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setParentClassTemplate("android.os.Parcelable")
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setKeepAnnotationOnClass() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            
            import android.support.annotation.Keep
            
            @Keep
            data class User(
                val company_name: String,
                val username: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setKeepAnnotationOnClass(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setKeepAnnotationOnClassAndroidX() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            
            import androidx.annotation.Keep
            
            @Keep
            data class User(
                val company_name: String,
                val username: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setKeepAnnotationOnClassAndroidX(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setKeepAnnotationAndPropertyInSameLine() {
        val input = """
            {"a":"a","Int":2}
        """.trimIndent()

        val expectedOutput = """
            
            import com.google.gson.annotations.SerializedName
            
            data class User(
                @SerializedName("a") val a: String,
                @SerializedName("Int") val int: Int
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setAnnotationLib(TargetJsonConverter.Gson)
                .setAnnotationAndPropertyInSameLine(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setParcelableSupport() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            
            import kotlinx.android.parcel.Parcelize
            import android.os.Parcelable
            
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class User(
                val company_name: String,
                val username: String
            ) : Parcelable
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setParcelableSupport(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertyPrefix() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val myprefix_Company_name: String,
                val myprefix_Username: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyPrefix("myprefix_")
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPropertySuffix() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val company_name_mysuffix: String,
                val username_mysuffix: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertySuffix("_mysuffix")
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setClassSuffix() {
        val input = """
            {"username": "john", "company_name": "ABC Ltd"}
        """.trimIndent()

        val expectedOutput = """
            data class UserMySuffix(
                val company_name: String,
                val username: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setClassSuffix("MySuffix")
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setForceInitDefaultValueWithOriginJsonValue() {
        val input = """
            {
              "users": [
                {
                  "username": "john",
                  "company_name": "ABC Ltd"
                },
                {
                  "username": "david",
                  "company_name": "XYZ Ltd"
                }
              ]
            }
        """.trimIndent()

        val expectedOutput = """
            data class User(
                val users: List<User> = listOf()
            ) {
                data class User(
                    val company_name: String = "XYZ Ltd",
                    val username: String = "david"
                )
            }
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setForceInitDefaultValueWithOriginJsonValue(true)
                .build(input, "User")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setForcePrimitiveTypeNonNullable() {
        val input = """
            { picture: { "id" : 1, "url" : "" } }
        """.trimIndent()

        val expectedOutput = """
            data class Test(
                val picture: Picture?
            ) {
                data class Picture(
                    val id: Int,
                    val url: String?
                )
            }
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPropertyTypeStrategy(PropertyTypeStrategy.Nullable)
                .setForcePrimitiveTypeNonNullable(true)
                .build(input, "Test")

        actualOutput.should.be.equal(expectedOutput)
    }

    @Test
    fun setPackageName() {
        val input = """
            {"name":"john"}
        """.trimIndent()

        val expectedOutput = """
            package com.my.package.name
            
            data class User(
                val name: String
            )
        """.trimIndent()

        val actualOutput = JsonToKotlinBuilder()
                .setPackageName("com.my.package.name")
                .build(input, "User")
        actualOutput.should.be.equal(expectedOutput)
    }
}