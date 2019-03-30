package wu.seal.jsontokotlin

import com.squareup.kotlinpoet.*
import wu.seal.jsontokotlin.bean.jsonschema.JsonObjectDef

class JsonSchemaDataClassGenerator(private val jsonObjectDef: JsonObjectDef) {
  val classes = mutableListOf<TypeSpec>()

  fun generate(className: String?) {
    val clazz = className
        ?: jsonObjectDef.title
        ?: throw IllegalArgumentException("className cannot be null when jsonObjectDef.title is null")

    val requiredFields = jsonObjectDef.required ?: emptyArray()
    val properties = if (ConfigManager.isOrderByAlphabetical) jsonObjectDef.properties.toSortedMap() else jsonObjectDef.properties
    val s = TypeSpec.classBuilder(clazz).apply {
      if (!ConfigManager.isCommentOff && (jsonObjectDef.description?.isNotBlank() == true)) {
        addKdoc(jsonObjectDef.description)
      }
      addModifiers(KModifier.DATA)
      primaryConstructor(FunSpec.constructorBuilder().apply {
        properties.forEach { property, propertyDefinition ->
          val type = JSON_SCHEMA_TYPE_MAPPINGS[propertyDefinition.type] ?: String::class
          if (type == Any::class) {
            val typeName = if (property !in requiredFields) {
              ClassName("", property.capitalize()).copy(nullable = true)
            } else ClassName("", property.capitalize())
            addParameter(property, typeName)
            val jsonSchemaDataClassGenerator = JsonSchemaDataClassGenerator(propertyDefinition)
            jsonSchemaDataClassGenerator.generate(typeName.simpleName)
            classes.addAll(jsonSchemaDataClassGenerator.classes)
          } else {
            val typeName = if (property !in requiredFields) {
              type.asTypeName().copy(nullable = true)
            } else type.asTypeName()

            addParameter(property, typeName)
          }
        }
      }.build())
      properties.forEach { property, propertyDefinition ->
        val type = JSON_SCHEMA_TYPE_MAPPINGS[propertyDefinition.type] ?: String::class
        if (type == Any::class) {
          val typeName = if (property !in requiredFields) {
            ClassName("", property.capitalize()).copy(nullable = true)
          } else ClassName("", property.capitalize())
          addProperty(
              PropertySpec.builder(property, typeName).apply {
                if (!ConfigManager.isCommentOff && (propertyDefinition.description?.isNotBlank() == true)) {
                  addKdoc(propertyDefinition.description)
                }
                initializer(property)
              }.build()
          )
        } else {
          val typeName = if (property !in requiredFields) {
            type.asTypeName().copy(nullable = true)
          } else type.asTypeName()

          addProperty(
              PropertySpec.builder(property, typeName).apply {
                if (!ConfigManager.isCommentOff && (propertyDefinition.description?.isNotBlank() == true)) {
                  addKdoc(propertyDefinition.description)
                }
                initializer(property)
              }.build()
          )
        }
      }
    }.build()
    classes.add(s)
  }
}
