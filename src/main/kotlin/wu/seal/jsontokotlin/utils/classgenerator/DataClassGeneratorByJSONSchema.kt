package wu.seal.jsontokotlin.utils.classgenerator

import wu.seal.jsontokotlin.JSON_SCHEMA_FORMAT_MAPPINGS
import wu.seal.jsontokotlin.model.classscodestruct.*
import wu.seal.jsontokotlin.model.codeelements.getDefaultValue
import wu.seal.jsontokotlin.model.jsonschema.JsonObjectDef
import wu.seal.jsontokotlin.model.jsonschema.JsonSchema
import wu.seal.jsontokotlin.model.jsonschema.PropertyDef
import wu.seal.jsontokotlin.utils.constToLiteral

class DataClassGeneratorByJSONSchema(private val rootClassName: String, private val jsonSchema: JsonSchema) {

    fun generate(): DataClass {
        val className = if (rootClassName.isNotBlank()) rootClassName else jsonSchema.title ?: ""
        if (className.isBlank()) throw IllegalArgumentException("className cannot be null when jsonSchema.title is null")
        return generateClass(jsonSchema, className)
    }

    private fun generateClass(jsonObjectDef: JsonObjectDef, className: String): DataClass {
        val description = jsonObjectDef.description
        val properties = getProperties(jsonObjectDef)
        return DataClass(
            name = className,
            properties = properties,
            comments = description ?: "",
            fromJsonSchema = true
        )
    }

    private fun getProperties(jsonObjectDef: JsonObjectDef): List<Property> {
        val properties = mutableListOf<Property>()
        if (jsonObjectDef.properties != null) {
            jsonObjectDef.properties.map { (propertyName, jsonProp) ->
                resolveProperty(jsonProp, propertyName, jsonObjectDef.required?.contains(propertyName) ?: false)
            }.toList().also { properties.addAll(it) }
        }
        if (jsonObjectDef.allOf != null) {
            jsonObjectDef.allOf.flatMap { getProperties(it) }.toList().also { properties.addAll(it) }
        }
        if (jsonObjectDef.oneOf != null) {
            jsonObjectDef.oneOf.flatMap { getProperties(it) }.toList().also { properties.addAll(it) }
        }
        if (jsonObjectDef.anyOf != null) {
            jsonObjectDef.anyOf.flatMap { getProperties(it) }.toList().also { properties.addAll(it) }
        }
        if (jsonObjectDef.ref != null) {
            getProperties(jsonSchema.resolveDefinition(jsonObjectDef.ref)).also { properties.addAll(it) }
        }
        if (jsonObjectDef.x_abstract == true) emptyList<Property>()
        return properties
    }

    private fun resolveProperty(
        jsonProp: PropertyDef,
        propertyName: String,
        isRequired: Boolean,
        includeConst: Boolean = true
    ): Property {
        val typeClass = if (jsonProp.typeString == "array") {
            val innerProperty = resolveProperty(
                jsonProp.items
                    ?: throw IllegalArgumentException("Array `items` must be defined (property: $propertyName)"),
                propertyName,
                false
            )
            GenericListClass(generic = innerProperty.typeObject)
        } else {
            val (jsonClassName, realDef) = getRealDefinition(jsonProp)
            resolveTypeClass(realDef.typeString, jsonClassName, realDef, propertyName)
        }
        val value = if (isRequired || !jsonProp.isTypeNullable) getDefaultValue(typeClass.name) else null
        return Property(
            originName = propertyName,
            originJsonValue = value,
            type = typeClass.name,
            comment = jsonProp.description ?: "",
            typeObject = typeClass,
            value = if (includeConst && jsonProp.const != null) constToLiteral(jsonProp.const) ?: "" else ""
        )
    }

    private fun resolveTypeClass(
        realDefJsonType: String?,
        jsonClassName: String?,
        realDef: PropertyDef,
        propertyName: String,
        checkEnum: Boolean = true,
        checkSealed: Boolean = true
    ): KotlinClass {
        val simpleName = jsonClassName ?: propertyName.capitalize()
        return when {
            checkEnum && realDef.enum != null -> resolveEnumClass(realDef, simpleName)
            checkSealed && realDef.anyOf != null -> resolveSealedClass(realDef, simpleName)
            realDefJsonType != null && (jsonClassName != null || realDefJsonType == "object") -> generateClass(
                realDef,
                simpleName
            )
            JSON_SCHEMA_FORMAT_MAPPINGS.containsKey(realDef.format) -> {
                object : UnModifiableNoGenericClass() {
                    override val name: String = JSON_SCHEMA_FORMAT_MAPPINGS[realDef.format]!!
                }
            }
            else -> when (realDefJsonType) {
                "string" -> KotlinClass.STRING
                "enum" -> KotlinClass.STRING
                "integer" -> KotlinClass.INT
                "number" -> KotlinClass.DOUBLE
                "boolean" -> KotlinClass.BOOLEAN
                else -> KotlinClass.ANY
            }
        }
    }

    private fun resolveEnumClass(enumDef: PropertyDef, name: String): KotlinClass {
        if (enumDef.enum == null) throw IllegalArgumentException("$name is not a enum")
        if (enumDef.x_enumNames != null && enumDef.enum.count() != enumDef.x_enumNames.count())
            throw IllegalArgumentException("$name enum values count ${enumDef.enum.count()} not equal to enum names count ${enumDef.x_enumNames}")
        val (jsonClassName, realDef) = getRealDefinition(enumDef)
        val typeClass =
            resolveTypeClass(realDef.typeString, jsonClassName, realDef, name, !jsonClassName.isNullOrBlank())
        return EnumClass(
            name = name,
            enum = enumDef.enum.asList(),
            xEnumNames = enumDef.x_enumNames?.asList(),
            generic = typeClass,
            comments = realDef.description
                ?: ""
        )
    }

    /** attempts to find a common set of discriminatory fields in `anyOf` members */
    private fun resolveDiscriminatoryProperties(realPropertyDefinitions: Array<PropertyDef>): List<Property> {
        // Only properties with constants values can be discriminatory
        var discriminatoryProperties =
            (realPropertyDefinitions.first().properties ?: mapOf())
                // Typed properties with a constant value
                .filter { it.value.const != null && it.value.typeString != null }
                // Property name to property
                .map { it.key to it.value }.toMap()

        // Discriminatory properties that are found across all properties
        for (property in realPropertyDefinitions) {
            discriminatoryProperties = discriminatoryProperties.filter {
                val properties = property.properties ?: hashMapOf()
                properties.containsKey(it.key) && properties[it.key]?.typeString == it.value.typeString
            }
        }

        return discriminatoryProperties.map {
            resolveProperty(
                it.value, it.key,
                isRequired = true,
                includeConst = false
            )
        }
    }


    private fun resolveSealedClass(def: PropertyDef, name: String): KotlinClass {
        val (jsonClassName, realDef) = getRealDefinition(def)

        // {<JSON_CLASS_NAME>=<PROPERTY_DEF>, ...}
        val referencedDefinitions = realDef.anyOf?.map {
            val realDefinition = getRealDefinition(it)
            realDefinition.first to realDefinition.second
        }?.toMap() ?: hashMapOf()

        val referencedClasses = referencedDefinitions.map {
            resolveTypeClass(
                realDefJsonType = it.value.typeString,
                jsonClassName = it.key,
                realDef = it.value,
                propertyName = "",
                // TODO: Evaluate nested sealed classes
                checkEnum = false,
                checkSealed = false
            )
        }

        val discriminatoryProperties = resolveDiscriminatoryProperties(
            referencedDefinitions.values.toTypedArray()
        )

        // Will always be `KotlinClass.ANY`
        // The `KotlinClass` constructor always requires a `generic` parameter that is
        // also a `KotlinClass`
        val typeClass = resolveTypeClass(
            realDef.typeString,
            jsonClassName,
            realDef,
            name,
            checkSealed = false
        )

        return SealedClass(
            name = name,
            generic = typeClass,
            comments = realDef.description ?: "",
            referencedClasses = referencedClasses,
            discriminatoryProperties = discriminatoryProperties
        )
    }

    /** resolves `ref`, `oneOf` and `allOf` then returns a real property definition */
    private fun getRealDefinition(def: PropertyDef): Pair<String? /* ClassName */, PropertyDef> {
        return when {
            (def.properties != null) -> Pair(def.tryGetClassName(), def)
            (def.ref != null) -> Pair(
                def.tryGetClassName(),
                getRealDefinition(jsonSchema.resolveDefinition(def.ref)).second
            )
            (def.oneOf != null) -> getRealDefinition(def.oneOf.firstOrNull { it.typeString != "null" }
                ?: def.oneOf.first())
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
