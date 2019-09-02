package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonElement
import wu.seal.jsontokotlin.bean.jsonschema.JsonSchema
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

class KotlinDataClassMaker(private val rootClassName: String, private val json: String) {

    fun makeKotlinDataClass(): KotlinDataClass {
        return if (json.isJSONSchema()) {
            val jsonSchema = Gson().fromJson(json, JsonSchema::class.java)
            DataClassGeneratorByJSONSchema(rootClassName,jsonSchema).generate()
        }else {
            val jsonObjectAfterOptimize = TargetJsonElement(json).getTargetJsonElementForGeneratingCode()
            if (jsonObjectAfterOptimize.isJsonObject) {
                DataClassGeneratorByJSONObject(rootClassName, jsonObjectAfterOptimize.asJsonObject).generate()
            } else {
                throw IllegalStateException("Can't generate Kotlin Data Class from a no JSON Object")
            }
        }
    }
    private fun String.isJSONSchema(): Boolean {
        val jsonElement = Gson().fromJson(this, JsonElement::class.java)
        return if (jsonElement.isJsonObject) {
            with(jsonElement.asJsonObject){
                has("\$schema")
            }
        } else {
            false
        }
    }
}


