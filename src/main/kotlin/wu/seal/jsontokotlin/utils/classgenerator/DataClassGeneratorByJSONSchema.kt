package wu.seal.jsontokotlin.utils.classgenerator

import wu.seal.jsontokotlin.model.jsonschema.JsonSchema
import wu.seal.jsontokotlin.model.classscodestruct.GenericListClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.model.codeelements.getDefaultValue
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
                            type = KotlinClass.STRING.name,
                            comment = schema.description ?: "",
                            typeObject = KotlinClass.STRING
                    )
                    properties.add(property)
                }
                "enum" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_STRING) else null
                    val property = Property(
                            originName = name,
                            originJsonValue = value,
                            type = KotlinClass.STRING.name,
                            comment = schema.description ?: "",
                            typeObject = KotlinClass.STRING
                    )
                    properties.add(property)
                }
                "integer" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_INT) else null
                    val property = Property(
                            originName = name,
                            originJsonValue = value,
                            type = KotlinClass.INT.name,
                            comment = schema.description ?: "",
                            typeObject = KotlinClass.INT
                    )
                    properties.add(property)
                }
                "number" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_DOUBLE) else null
                    val property = Property(
                            originName = name,
                            originJsonValue = value,
                            type = KotlinClass.DOUBLE.name,
                            comment = schema.description ?: "",
                            typeObject = KotlinClass.DOUBLE
                    )
                    properties.add(property)
                }
                "boolean" -> {
                    val value = if (requires.contains(name)) getDefaultValue(TYPE_BOOLEAN) else null
                    val property = Property(
                            originName = name,
                            originJsonValue = value,
                            type = KotlinClass.BOOLEAN.name,
                            comment = schema.description ?: "",
                            typeObject = KotlinClass.BOOLEAN
                    )
                    properties.add(property)
                }
                "array" -> {
                    val itemType = getItemTypeOfArray(schema)
                    val type = GenericListClass(generic = itemType)
                    val value = if (requires.contains(name)) getDefaultValue(type.name) else null
                    val property = Property(
                            originName = name,
                            originJsonValue = value,
                            type = type.name,
                            comment = schema.description ?: "",
                            typeObject = type
                    )
                    properties.add(property)
                }
                else -> throw UnsupportedOperationException("Can't support this type in jsonschema ${jsonSchema.type}")
            }
        }
        return KotlinDataClass(name = rootClassName, properties = properties)
    }

    private fun getItemTypeOfArray(schema: JsonSchema): KotlinClass {
        return if (schema.itemsOfArray?.isJsonObject == true) {
            when (schema.itemsOfArray.asJsonObject["type"].asString) {
                "string" -> KotlinClass.STRING
                "enum" -> KotlinClass.STRING
                "integer" -> KotlinClass.INT
                "number" -> KotlinClass.DOUBLE
                "boolean" -> KotlinClass.BOOLEAN
                else -> KotlinClass.ANY
            }
        } else KotlinClass.ANY
    }

}
