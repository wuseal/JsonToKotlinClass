package wu.seal.jsontokotlin.utils.classgenerator

import com.google.gson.JsonObject
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.utils.TargetJsonElement
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-08-18
 * Description: Generate Kotlin Data class Struct from JSON Object
 */
class DataClassGeneratorByJSONObject(private val className: String, private val jsonObject: JsonObject) {

    fun generate(): DataClass {
        if (maybeJsonObjectBeMapType(jsonObject) && ConfigManager.enableMapType) {
            throw IllegalArgumentException("Can't generate data class from a Map type JSONObjcet when enable Map Type : $jsonObject")
        }
        val properties = mutableListOf<Property>()

        jsonObject.entrySet().forEach { (jsonKey, jsonValue) ->
            when {
                jsonValue.isJsonNull -> {
                    val jsonValueNullProperty =
                            Property(originName = jsonKey, originJsonValue = null, type = KotlinClass.ANY.name, comment = "null", typeObject = KotlinClass.ANY)
                    properties.add(jsonValueNullProperty)
                }

                jsonValue.isJsonPrimitive -> {
                    val type = jsonValue.asJsonPrimitive.toKotlinClass()
                    val jsonValuePrimitiveProperty =
                            Property(
                                    originName = jsonKey,
                                    originJsonValue = jsonValue.asString,
                                    type = type.name,
                                    comment = jsonValue.asString,
                                    typeObject = type
                            )
                    properties.add(jsonValuePrimitiveProperty)
                }

                jsonValue.isJsonArray -> {
                    val arrayType = GenericListClassGeneratorByJSONArray(jsonKey, jsonValue.toString()).generate()
                    val jsonValueArrayProperty =
                            Property(originName = jsonKey, value = "", type = arrayType.name, typeObject = arrayType)
                    properties.add(jsonValueArrayProperty)
                }
                jsonValue.isJsonObject -> {
                    jsonValue.asJsonObject.run {
                        if (ConfigManager.enableMapType && maybeJsonObjectBeMapType(this)) {
                            var refDataClass: DataClass? = null
                            val mapKeyType = getMapKeyTypeConvertFromJsonObject(this)
                            val mapValueType = getMapValueTypeConvertFromJsonObject(this)
                            if (mapValueIsObjectType(mapValueType)) {
                                val targetJsonElement =
                                        TargetJsonElement(entrySet().first().value).getTargetJsonElementForGeneratingCode()
                                if (targetJsonElement.isJsonObject) {
                                    refDataClass = DataClassGeneratorByJSONObject(
                                            getChildType(mapValueType),
                                            targetJsonElement.asJsonObject
                                    ).generate()
                                } else {
                                    throw IllegalStateException("Don't support No JSON Object Type for Generate Kotlin Data Class")
                                }
                            }
                            val mapType = "Map<$mapKeyType,$mapValueType>"
                            val jsonValueObjectMapTypeProperty = Property(
                                    originName = jsonKey,
                                    originJsonValue = "",
                                    type = mapType,
                                    typeObject = refDataClass ?: KotlinClass.ANY
                            )
                            properties.add(jsonValueObjectMapTypeProperty)
                        } else {
                            var refDataClass: DataClass? = null
                            val type = getJsonObjectType(jsonKey)
                            val targetJsonElement =
                                    TargetJsonElement(this).getTargetJsonElementForGeneratingCode()
                            if (targetJsonElement.isJsonObject) {
                                refDataClass = DataClassGeneratorByJSONObject(
                                        getRawType(type),
                                        targetJsonElement.asJsonObject
                                ).generate()

                            } else {
                                throw IllegalStateException("Don't support No JSON Object Type for Generate Kotlin Data Class")
                            }

                            val jsonValueObjectProperty = Property(
                                    originName = jsonKey,
                                    originJsonValue = "",
                                    type = type,
                                    typeObject = refDataClass
                            )
                            properties.add(jsonValueObjectProperty)
                        }
                    }
                }
            }
        }

        val propertiesAfterConsumeBackStageProperties = properties.consumeBackstageProperties()
        return DataClass(name = className, properties = propertiesAfterConsumeBackStageProperties)
    }

    private fun mapValueIsObjectType(mapValueType: String) = (mapValueType == MAP_DEFAULT_OBJECT_VALUE_TYPE
            || mapValueType.contains(MAP_DEFAULT_ARRAY_ITEM_VALUE_TYPE))

    /**
     * Consume the properties whose name end with [BACKSTAGE_NULLABLE_POSTFIX],
     * After call this method, all properties's name end with [BACKSTAGE_NULLABLE_POSTFIX] will be removed
     * And the corresponding properies whose name without 【BACKSTAGE_NULLABLE_POSTFIX】it's value will be set
     * to null,for example:
     * remove property -> name = demoProperty__&^#
     * set null value  -> demoProperty.value = null
     */
    private fun List<Property>.consumeBackstageProperties(): List<Property> {
        val newProperties = mutableListOf<Property>()
        val nullableBackstagePropertiesNames = filter { it.name.endsWith(BACKSTAGE_NULLABLE_POSTFIX) }.map { it.name }
        val nullablePropertiesNames = nullableBackstagePropertiesNames.map { it.removeSuffix(BACKSTAGE_NULLABLE_POSTFIX) }
        forEach {
            when {
                nullablePropertiesNames.contains(it.name) -> newProperties.add(it.copy(originJsonValue = null, value = ""))
                nullableBackstagePropertiesNames.contains(it.name) -> {
                    //when hit the backstage property just continue, don't add it to new properties
                }
                else -> newProperties.add(it)
            }
        }
        return newProperties
    }
}


