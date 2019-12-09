package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.bean.jsonschema.JsonSchema
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.utils.*

class DataClassGeneratorByJSONSchema(private val rootClassName: String, private val jsonSchema: JsonSchema) {

    fun generate(): KotlinDataClass {
        val requires = jsonSchema.required
        val properties = mutableListOf<Property>()
        jsonSchema.properties.forEach { (name, schema) ->
            when (schema.type) {
                "object" -> {
                    val dataClass = DataClassGeneratorByJSONSchema(name, schema).generate()
                    val value = if (requires.contains(name)) getDefaultValue(name) else null
                    val property =
                        Property(
                            originName = name,
                            originJsonValue = value,
                            type = name,
                            typeObject = dataClass,
                            comment = schema.description ?: ""
                        )
                    properties.add(property)
                }
                "string" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_STRING) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = TYPE_STRING,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                "enum" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_STRING) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = TYPE_STRING,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                "integer" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_INT) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = TYPE_INT,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                "number" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_DOUBLE) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = TYPE_DOUBLE,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                "boolean" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_BOOLEAN) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = TYPE_BOOLEAN,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                "array" -> {
                    val itemType = getItemTypeOfArray(schema)
                    val type = "List<$itemType>"
                    val value = if (requires.contains(name)) getDefaultValue(type) else null
                    val property = Property(
                        originName = name,
                        originJsonValue = value,
                        type = type,
                        comment = schema.description ?: ""
                    )
                    properties.add(property)
                }
                else -> throw UnsupportedOperationException("Can't support this type in jsonschema ${jsonSchema.type}")
            }
        }
        return KotlinDataClass(name = rootClassName, properties = properties)
    }

    private fun getItemTypeOfArray(schema: JsonSchema): String {
        return if (schema.itemsOfArray?.isJsonObject == true) {
            when (schema.itemsOfArray.asJsonObject["type"].asString) {
                "string" -> TYPE_STRING
                "enum" -> TYPE_STRING
                "integer" -> TYPE_INT
                "number" -> TYPE_DOUBLE
                "boolean" -> TYPE_BOOLEAN
                else -> DEFAULT_TYPE
            }
        } else DEFAULT_TYPE
    }

}
