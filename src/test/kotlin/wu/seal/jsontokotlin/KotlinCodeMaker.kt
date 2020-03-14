package wu.seal.jsontokotlin

import com.google.gson.Gson
import wu.seal.jsontokotlin.jsonschema.JsonSchemaDataClassGenerator
import wu.seal.jsontokotlin.jsonschema.models.JsonSchema
import wu.seal.jsontokotlin.utils.KotlinClassCodeMaker
import wu.seal.jsontokotlin.utils.KotlinClassMaker

/**
 * Kotlin code maker
 * Created by seal.wu on 2017/8/21.
 */
class KotlinCodeMaker(private val className: String, private val inputJson: String) {

    fun makeKotlinData(): String {
        val kotlinClass = KotlinClassMaker(
                className,
                inputJson
        ).makeKotlinClass()
        return KotlinClassCodeMaker(
                kotlinClass
        ).makeKotlinClassCode()
    }

    @Throws
    fun parseJSONSchema(): String {
        val jsonSchema = Gson().fromJson<JsonSchema>(inputJson, JsonSchema::class.java)
        val classes = JsonSchemaDataClassGenerator(jsonSchema, if (className.isBlank()) null else className).generate()
        //`classes` can be also saved into separated files since it's a map
        return classes.values.joinToString("\n") { it.toString() }
    }
}
