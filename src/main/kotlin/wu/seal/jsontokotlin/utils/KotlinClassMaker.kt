package wu.seal.jsontokotlin.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import wu.seal.jsontokotlin.model.jsonschema.JsonSchema
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONObject
import wu.seal.jsontokotlin.utils.classgenerator.DataClassGeneratorByJSONSchema
import wu.seal.jsontokotlin.utils.classgenerator.ListClassGeneratorByJSONArray

class KotlinClassMaker(private val rootClassName: String, private val json: String) {

    fun makeKotlinClass(): KotlinClass {
        return if (json.isJSONSchema()) {
            val jsonSchema = Gson().fromJson(json, JsonSchema::class.java)
            DataClassGeneratorByJSONSchema(rootClassName, jsonSchema).generate()
        } else {
            when {
                json.isJSONObject() -> DataClassGeneratorByJSONObject(rootClassName, Gson().fromJson(json, JsonObject::class.java)).generate()
                json.isJSONArray() -> ListClassGeneratorByJSONArray(rootClassName, json).generate()
                else -> throw IllegalStateException("Can't generate Kotlin Data Class from a no JSON Object/JSON Object Array")
            }
        }
    }

    private fun String.isJSONSchema(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return if (jsonElement.isJsonObject) {
            with(jsonElement.asJsonObject) {
                has("\$schema")
            }
        } else {
            false
        }
    }
    private fun String.isJSONObject(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return jsonElement.isJsonObject
    }

    private fun String.isJSONArray(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return jsonElement.isJsonArray
    }
}


