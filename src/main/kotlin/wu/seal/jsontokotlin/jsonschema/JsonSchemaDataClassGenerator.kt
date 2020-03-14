package wu.seal.jsontokotlin.jsonschema

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

import wu.seal.jsontokotlin.jsonschema.models.JsonObjectDef
import wu.seal.jsontokotlin.jsonschema.models.JsonSchema
import wu.seal.jsontokotlin.jsonschema.models.PropertyDef
import wu.seal.jsontokotlin.model.ConfigManager
import kotlin.String

class JsonSchemaDataClassGenerator(private val jsonSchema: JsonSchema, private val mainClassName: String?) {

    /* returns map of generated classes and enums (className, typeSpec) */
    fun generate(): Map<String, TypeSpec> {
        val className = mainClassName
                ?: jsonSchema.title
                ?: throw IllegalArgumentException("className cannot be null when jsonSchema.title is null")
        return generateClass(jsonSchema, className)
    }

    private fun generateClass(jsonObjectDef: JsonObjectDef, className: String): Map<String, TypeSpec> {
        val classes = mutableMapOf<String, TypeSpec>()

        val properties = getProperties(jsonObjectDef)
        val classSpec = TypeSpec.classBuilder(className).apply {
            if (!ConfigManager.isCommentOff && (jsonObjectDef.description?.isNotBlank() == true)) {
                addKdoc(jsonObjectDef.description)
            }
            addModifiers(KModifier.DATA)
            primaryConstructor(FunSpec.constructorBuilder().apply {
                properties.forEach { property ->
                    addParameter(property.name, property.arrayTypeName ?: property.typeName)
                }
            }.build())

            properties.forEach { property ->
                addProperty(
                        PropertySpec.builder(property.name, property.arrayTypeName ?: property.typeName).apply {
                            if (!ConfigManager.isCommentOff && (property.jsonProp.description?.isNotBlank() == true)) {
                                addKdoc(property.jsonProp.description)
                            }
                            initializer(property.name)
                        }.build()
                )
            }
        }.build()

        classes[className] = classSpec

        properties.forEach {
            val strTypeName = it.typeName.simpleName
            if (!classes.containsKey(strTypeName))
                if (it.realDef.enum != null)
                    classes[strTypeName] = generateEnum(it.realDef, strTypeName)
                else if (it.realDef.typeString == "object" && it.realDef.properties?.any() == true)
                    classes.putAll(generateClass(it.realDef, strTypeName))
        }

        return classes
    }

    private fun generateEnum(enumDef: PropertyDef, name: String): TypeSpec {
        if (enumDef.enum == null) throw IllegalArgumentException("$name is not a enum")
        if (enumDef.x_enumNames != null && enumDef.enum.count() != enumDef.x_enumNames.count())
            throw IllegalArgumentException("$name enum values count ${enumDef.enum.count()} not equal to enum names count ${enumDef.x_enumNames}")

        val enumType = JSON_SCHEMA_TYPE_MAPPINGS[enumDef.typeString]!!

        return TypeSpec.enumBuilder(name).apply {
            if (!ConfigManager.isCommentOff && (enumDef.description?.isNotBlank() == true)) {
                addKdoc(enumDef.description)
            }

            val typeName = ClassName("", enumType.simpleName!!)
            primaryConstructor(FunSpec.constructorBuilder().apply {
                addParameter("value", typeName)
            }.build())
            addProperty(PropertySpec.builder("value", typeName).initializer("value").build())

            for (i in 0 until enumDef.enum.count()) {
                val constantValue: Any = if (enumType == Int::class)
                    (enumDef.enum[i] as Double).toInt()
                else
                    enumDef.enum[i].toString()
                val constantName = enumDef.x_enumNames?.get(i)
                        ?: if (constantValue is Int) "_$constantValue" else constantValue.toString()

                addEnumConstant(
                        constantName,
                        TypeSpec.anonymousClassBuilder()
                                .addSuperclassConstructorParameter(if (enumType == String::class) "%S" else "%L", constantValue)
                                .build()
                )
            }

        }.build()
    }

    data class PropertyInfo(
            val name: String,
            val description: String?,
            val jsonProp: PropertyDef,
            val realDef: PropertyDef,

            val typeName: ClassName,
            var arrayTypeName: ParameterizedTypeName? = null
    ) {
        fun isArray() = arrayTypeName != null
    }

    private fun getProperties(jsonObjectDef: JsonObjectDef): List<PropertyInfo> {
        return when {
            jsonObjectDef.properties != null -> jsonObjectDef.properties.map { (propertyName, jsonProp) ->
                resolveProperty(jsonProp, propertyName)
            }.toList()
            jsonObjectDef.allOf != null -> jsonObjectDef.allOf.flatMap { getProperties(it) }.toList()
            jsonObjectDef.ref != null -> getProperties(jsonSchema.resolveDefinition(jsonObjectDef.ref))
            jsonObjectDef.x_abstract == true -> listOf()
            else -> throw NotSupportedException("Unknown jsonObjectDef")
        }
    }

    private fun resolveProperty(jsonProp: PropertyDef, propertyName: String): PropertyInfo {
        return if (jsonProp.typeString == "array") {
            val innerProperty = resolveProperty(jsonProp.items
                    ?: throw IllegalArgumentException("Array `items` must be defined (property: $propertyName)"), propertyName)

            val innerType = innerProperty.arrayTypeName ?: innerProperty.typeName
            var arrayTypeName = ClassName("", List::class.simpleName!!) //TODO array type customization
                    .parameterizedBy(innerType)
            if (jsonProp.isTypeNullable)
                arrayTypeName = arrayTypeName.copy(nullable = true) as ParameterizedTypeName

            PropertyInfo(propertyName, jsonProp.description, jsonProp, innerProperty.realDef, innerProperty.typeName, arrayTypeName)
        } else {
            val (jsonClassName, realDef) = getRealDefinition(jsonProp)
            val typeName = resolveType(realDef.typeString, jsonClassName, jsonProp, propertyName)

            PropertyInfo(propertyName, jsonProp.description, jsonProp, realDef, typeName)
        }
    }

    private fun resolveType(jsonType: String?, jsonClassName: String?, jsonProp: PropertyDef, propertyName: String): ClassName {
        var typeName = when {
            jsonType != null && (jsonClassName != null || jsonType == "object" || jsonProp.enum != null) -> {
                val simpleName = jsonClassName ?: propertyName.capitalize()
                ClassName("", simpleName)
            }
            JSON_SCHEMA_FORMAT_MAPPINGS.containsKey(jsonProp.format) -> {
                ClassName.bestGuess(JSON_SCHEMA_FORMAT_MAPPINGS[jsonProp.format]!!)
            }
            else -> {
                val type = JSON_SCHEMA_TYPE_MAPPINGS[jsonType] ?: Any::class //type can be null in `items` property
                ClassName("", type.simpleName!!)
            }
        } //TODO handle `format` (like date-time)

        if (jsonProp.isTypeNullable)
            typeName = typeName.copy(nullable = true) as ClassName
        return typeName
    }

    /** resolves `ref`, `oneOf` and `allOf` then returns a real property definition */
    private fun getRealDefinition(def: PropertyDef): Pair<String? /* ClassName */, PropertyDef> {
        return when {
            (def.ref != null) -> Pair(def.tryGetClassName(), getRealDefinition(jsonSchema.resolveDefinition(def.ref)).second)
            (def.oneOf != null) -> if (def.oneOf.count() > 1)
                getRealDefinition(def.oneOf.first { it.typeString != "null" })
            else
                getRealDefinition(def.oneOf[0])
            (def.allOf != null) -> {
                val combinedProps: MutableMap<String, PropertyDef> = mutableMapOf()
                def.allOf.forEach { p ->
                    val realDef = if (p.properties == null) getRealDefinition(p).second else p
                    if (realDef.properties != null)
                        combinedProps.putAll(realDef.properties.map { pair -> pair.key to getRealDefinition(pair.value).second })
                }
                val combined = PropertyDef(type = "object", properties = combinedProps)

                Pair(null, combined)
            }
            else -> Pair(null, def)
        }
    }

}
