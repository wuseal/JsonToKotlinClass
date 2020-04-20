package wu.seal.jsontokotlinclass.server

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlinclass.server.controllers.GenerateController
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.DefaultValueStrategy as DefaultValueStrategy1
import wu.seal.jsontokotlin.TargetJsonConverter as TargetJsonConverter1

class GenerateControllerTest {

    lateinit var mockMvc: MockMvc
    lateinit var objectMapper: ObjectMapper

    @Before
    fun setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                GenerateController()
        ).build()

        this.objectMapper = ObjectMapper()
    }

    @Test
    fun simpleTest() {

        val request = GenerateRequest(
                """
                    {"name":"theapache64"}
                """.trimIndent(),
                className = "Person",
                annotationLib = TargetJsonConverter1.MoShi.name,
                defaultValueStrategy = DefaultValueStrategy1.AvoidNull.name,
                propertyTypeStrategy = PropertyTypeStrategy.Nullable.name,
                indent = 8,
                commentsEnabled = true,
                createAnnotationOnlyWhenNeededEnabled = false,
                enableVarProperties = true,
                forceInitDefaultValueWithOriginJsonValueEnabled = false,
                forcePrimitiveTypeNonNullableEnabled = true,
                innerClassModelEnabled = true,
                keepAnnotationOnClassAndroidXEnabled = false,
                keepAnnotationOnClassEnabled = true,
                mapTypeEnabled = true,
                orderByAlphabeticEnabled = true,
                parcelableSupportEnabled = false,
                propertyAndAnnotationInSameLineEnabled = false,
                classSuffix = null,
                packageName = null,
                parentClassTemplate = null,
                propertyPrefix = null,
                propertySuffix = null
        )

        val requestJson = objectMapper.writeValueAsString(request)
        mockMvc.perform(post("/generate").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string("{\"data\":{\"code\":\"data class Person(\\n        val name: String\\n)\"},\"error\":false,\"error_code\":-1,\"message\":\"OK\"}"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
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
                className = "Example",
                annotationLib = TargetJsonConverter1.MoShi.name,
                defaultValueStrategy = DefaultValueStrategy1.AvoidNull.name,
                propertyTypeStrategy = PropertyTypeStrategy.Nullable.name,
                indent = 8,
                commentsEnabled = true,
                createAnnotationOnlyWhenNeededEnabled = false,
                enableVarProperties = true,
                forceInitDefaultValueWithOriginJsonValueEnabled = false,
                forcePrimitiveTypeNonNullableEnabled = true,
                innerClassModelEnabled = true,
                keepAnnotationOnClassAndroidXEnabled = false,
                keepAnnotationOnClassEnabled = true,
                mapTypeEnabled = true,
                orderByAlphabeticEnabled = true,
                parcelableSupportEnabled = false,
                propertyAndAnnotationInSameLineEnabled = false,
                classSuffix = "MyClassSuffix",
                packageName = "com.my.package.name",
                parentClassTemplate = null,
                propertyPrefix = "prop_prefix_",
                propertySuffix = "_prop_suffix"
        )

        val response = controller.generate(request)
        val actualOutput = ObjectMapper().writeValueAsString(response)
        println(actualOutput)
        val expectedOutput = """
            {"data":{"code":"package com.my.package.name\n\n\nimport com.squareup.moshi.Json\nimport android.support.annotation.Keep\n\n@Keep\ndata class ExampleMyClassSuffix(\n        @Json(name = \"glossary\")\n        var prop_prefix_GlossaryProp_prefix_: GlossaryMyClassSuffix? = GlossaryMyClassSuffix()\n) {\n        @Keep\n        data class GlossaryMyClassSuffix(\n                @Json(name = \"GlossDiv\")\n                var prop_prefix_GlossDivProp_prefix_: GlossDivMyClassSuffix? = GlossDivMyClassSuffix(),\n                @Json(name = \"title\")\n                var prop_prefix_TitleProp_prefix_: String? = \"\" // example glossary\n        ) {\n                @Keep\n                data class GlossDivMyClassSuffix(\n                        @Json(name = \"GlossList\")\n                        var prop_prefix_GlossListProp_prefix_: GlossListMyClassSuffix? = GlossListMyClassSuffix(),\n                        @Json(name = \"title\")\n                        var prop_prefix_TitleProp_prefix_: String? = \"\" // S\n                ) {\n                        @Keep\n                        data class GlossListMyClassSuffix(\n                                @Json(name = \"GlossEntry\")\n                                var prop_prefix_GlossEntryProp_prefix_: GlossEntryMyClassSuffix? = GlossEntryMyClassSuffix()\n                        ) {\n                                @Keep\n                                data class GlossEntryMyClassSuffix(\n                                        @Json(name = \"Abbrev\")\n                                        var prop_prefix_AbbrevProp_prefix_: String? = \"\", // ISO 8879:1986\n                                        @Json(name = \"Acronym\")\n                                        var prop_prefix_AcronymProp_prefix_: String? = \"\", // SGML\n                                        @Json(name = \"GlossDef\")\n                                        var prop_prefix_GlossDefProp_prefix_: GlossDefMyClassSuffix? = GlossDefMyClassSuffix(),\n                                        @Json(name = \"GlossSee\")\n                                        var prop_prefix_GlossSeeProp_prefix_: String? = \"\", // markup\n                                        @Json(name = \"GlossTerm\")\n                                        var prop_prefix_GlossTermProp_prefix_: String? = \"\", // Standard Generalized Markup Language\n                                        @Json(name = \"ID\")\n                                        var prop_prefix_IDProp_prefix_: String? = \"\", // SGML\n                                        @Json(name = \"SortAs\")\n                                        var prop_prefix_SortAsProp_prefix_: String? = \"\" // SGML\n                                ) {\n                                        @Keep\n                                        data class GlossDefMyClassSuffix(\n                                                @Json(name = \"GlossSeeAlso\")\n                                                var prop_prefix_GlossSeeAlsoProp_prefix_: List<String?>? = listOf(),\n                                                @Json(name = \"para\")\n                                                var prop_prefix_ParaProp_prefix_: String? = \"\" // A meta-markup language, used to create markup languages such as DocBook.\n                                        )\n                                }\n                        }\n                }\n        }\n}"},"error":false,"error_code":-1,"message":"OK"}
        """.trimIndent()
        assertEquals(expectedOutput, actualOutput)
    }
}