package wu.seal.jsontokotlin.server.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest

class GenerateControllerTest {

    @Test
    fun simpleTest() {
        val controller = GenerateController()
        val request = GenerateRequest(
                """
                    {"name":"theapache64"}
                """.trimIndent(),
                "Person"
        )

        val response = controller.generate(request)
        val actualOutput = ObjectMapper().writeValueAsString(response)
        val expectedOutput = """
            {"data":{"code":"data class Person(\n    val name: String\n)"},"error":true,"error_code":-1,"message":"OK"}
        """.trimIndent()
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun complexTest() {
        val controller = GenerateController()
        val request = GenerateRequest(
                """
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
                """.trimIndent(),
                "Example",
                TargetJsonConverter.MoShi.name,
                "MyClassSuffix",
                DefaultValueStrategy.AvoidNull.name,
                8,
                isCommentsEnabled = true,
                isCreateAnnotationOnlyWhenNeededEnabled = false,
                isEnableVarProperties = true,
                isForceInitDefaultValueWithOriginJsonValueEnabled = false,
                isForcePrimitiveTypeNonNullableEnabled = true,
                isInnerClassModelEnabled = true,
                isKeepAnnotationOnClassAndroidXEnabled = false,
                isKeepAnnotationOnClassEnabled = true,
                isMapTypeEnabled = true,
                isOrderByAlphabeticEnabled = true,
                isParcelableSupportEnabled = false,
                isPropertyAndAnnotationInSameLineEnabled = false,
                packageName = "com.my.package.name",
                parentClassTemplate = null,
                propertyPrefix = "prop_prefix_",
                propertySuffix = "_prop_suffix",
                propertyTypeStrategy = PropertyTypeStrategy.Nullable.name
        )

        val response = controller.generate(request)
        val actualOutput = ObjectMapper().writeValueAsString(response)
        println(actualOutput)
        val expectedOutput = """
            {"data":{"code":"package com.my.package.name\n\n\nimport com.squareup.moshi.Json\nimport android.support.annotation.Keep\n\n@Keep\ndata class ExampleMyClassSuffix(\n        @Json(name = \"glossary\")\n        var prop_prefix_GlossaryProp_prefix_: GlossaryMyClassSuffix? = GlossaryMyClassSuffix()\n) {\n        @Keep\n        data class GlossaryMyClassSuffix(\n                @Json(name = \"GlossDiv\")\n                var prop_prefix_GlossDivProp_prefix_: GlossDivMyClassSuffix? = GlossDivMyClassSuffix(),\n                @Json(name = \"title\")\n                var prop_prefix_TitleProp_prefix_: String? = \"\" // example glossary\n        ) {\n                @Keep\n                data class GlossDivMyClassSuffix(\n                        @Json(name = \"GlossList\")\n                        var prop_prefix_GlossListProp_prefix_: GlossListMyClassSuffix? = GlossListMyClassSuffix(),\n                        @Json(name = \"title\")\n                        var prop_prefix_TitleProp_prefix_: String? = \"\" // S\n                ) {\n                        @Keep\n                        data class GlossListMyClassSuffix(\n                                @Json(name = \"GlossEntry\")\n                                var prop_prefix_GlossEntryProp_prefix_: GlossEntryMyClassSuffix? = GlossEntryMyClassSuffix()\n                        ) {\n                                @Keep\n                                data class GlossEntryMyClassSuffix(\n                                        @Json(name = \"Abbrev\")\n                                        var prop_prefix_AbbrevProp_prefix_: String? = \"\", // ISO 8879:1986\n                                        @Json(name = \"Acronym\")\n                                        var prop_prefix_AcronymProp_prefix_: String? = \"\", // SGML\n                                        @Json(name = \"GlossDef\")\n                                        var prop_prefix_GlossDefProp_prefix_: GlossDefMyClassSuffix? = GlossDefMyClassSuffix(),\n                                        @Json(name = \"GlossSee\")\n                                        var prop_prefix_GlossSeeProp_prefix_: String? = \"\", // markup\n                                        @Json(name = \"GlossTerm\")\n                                        var prop_prefix_GlossTermProp_prefix_: String? = \"\", // Standard Generalized Markup Language\n                                        @Json(name = \"ID\")\n                                        var prop_prefix_IDProp_prefix_: String? = \"\", // SGML\n                                        @Json(name = \"SortAs\")\n                                        var prop_prefix_SortAsProp_prefix_: String? = \"\" // SGML\n                                ) {\n                                        @Keep\n                                        data class GlossDefMyClassSuffix(\n                                                @Json(name = \"GlossSeeAlso\")\n                                                var prop_prefix_GlossSeeAlsoProp_prefix_: List<String?>? = listOf(),\n                                                @Json(name = \"para\")\n                                                var prop_prefix_ParaProp_prefix_: String? = \"\" // A meta-markup language, used to create markup languages such as DocBook.\n                                        )\n                                }\n                        }\n                }\n        }\n}"},"error":true,"error_code":-1,"message":"OK"}
        """.trimIndent()
        assertEquals(expectedOutput, actualOutput)
    }
}