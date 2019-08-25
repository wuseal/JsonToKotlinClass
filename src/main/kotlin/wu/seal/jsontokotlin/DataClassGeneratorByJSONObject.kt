package wu.seal.jsontokotlin

import com.google.gson.JsonObject
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import wu.seal.jsontokotlin.utils.*

/**
 * Created by Seal.Wu on 2019-08-18
 * Description: Generate Kotlin Data class Struct from JSON Object
 */
class DataClassGeneratorByJSONObject(private val className: String, private val jsonObjectAfterOptimize: JsonObject) {

    fun generate(): KotlinDataClass {
        if (maybeJsonObjectBeMapType(jsonObjectAfterOptimize) && ConfigManager.enableMapType) {
            throw IllegalArgumentException("Can't generate data class from a Map type JSONObjcet when enable Map Type : $jsonObjectAfterOptimize")
        }
        val properties = mutableListOf<Property>()

        jsonObjectAfterOptimize.entrySet().forEach { (jsonKey, jsonValue) ->
            when {
                jsonValue.isJsonNull -> {
                    val jsonValueNullProperty =
                        Property(originName = jsonKey, originJsonValue = null, type = DEFAULT_TYPE, comment = "null")
                    properties.add(jsonValueNullProperty)
                }

                jsonValue.isJsonPrimitive -> {
                    val type = getPrimitiveType(jsonValue.asJsonPrimitive)
                    val jsonValuePrimitiveProperty =
                        Property(
                            originName = jsonKey,
                            originJsonValue = jsonValue.asString,
                            type = type,
                            comment = jsonValue.asString
                        )
                    properties.add(jsonValuePrimitiveProperty)
                }

                jsonValue.isJsonArray -> {
                    jsonValue.asJsonArray.apply {
                        val type = getArrayType(jsonKey, this)
                        var refKotlinDataClass: KotlinDataClass? = null
                        if (!allChildrenAreEmptyArray()) {
                            if (isExpectedJsonObjArrayType(this) || onlyHasOneObjectElementRecursive()
                                || onlyHasOneSubArrayAndAllItemsAreObjectElementRecursive()
                            ) {
                                val targetJsonElement =
                                    TargetJsonElement(jsonValue).getTargetJsonElementForGeneratingCode()
                                if (targetJsonElement.isJsonObject) {
                                    val jObject = targetJsonElement.asJsonObject
                                    refKotlinDataClass = DataClassGeneratorByJSONObject(
                                        getChildType(getRawType(type)),
                                        jObject
                                    ).generate()

                                } else {
                                    throw IllegalStateException("Don't support No JSON Object Type for Generate Kotlin Data Class")
                                }
                            }
                        }
                        val jsonValueArrayProperty =
                            Property(originName = jsonKey, value = "", type = type, typeObject = refKotlinDataClass)
                        properties.add(jsonValueArrayProperty)
                    }
                }
                jsonValue.isJsonObject -> {
                    jsonValue.asJsonObject.run {
                        if (ConfigManager.enableMapType && maybeJsonObjectBeMapType(this)) {
                            var refKotlinDataClass: KotlinDataClass? = null
                            val mapKeyType = getMapKeyTypeConvertFromJsonObject(this)
                            val mapValueType = getMapValueTypeConvertFromJsonObject(this)
                            if (mapValueIsObjectType(mapValueType)) {
                                val targetJsonElement =
                                    TargetJsonElement(entrySet().first().value).getTargetJsonElementForGeneratingCode()
                                if (targetJsonElement.isJsonObject) {
                                    refKotlinDataClass = DataClassGeneratorByJSONObject(
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
                                typeObject = refKotlinDataClass
                            )
                            properties.add(jsonValueObjectMapTypeProperty)
                        } else {
                            var refKotlinDataClass: KotlinDataClass? = null
                            val type = getJsonObjectType(jsonKey)
                            val targetJsonElement =
                                TargetJsonElement(this).getTargetJsonElementForGeneratingCode()
                            if (targetJsonElement.isJsonObject) {
                                refKotlinDataClass = DataClassGeneratorByJSONObject(
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
                                typeObject = refKotlinDataClass
                            )
                            properties.add(jsonValueObjectProperty)
                        }
                    }
                }
            }
        }

        val propertiesAfterConsumeBackStageProperties = properties.consumeBackstageProperties()
        return KotlinDataClass(name = className, properties = propertiesAfterConsumeBackStageProperties)
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
                nullablePropertiesNames.contains(it.name) -> newProperties.add(it.copy(originJsonValue = null,value = ""))
                nullableBackstagePropertiesNames.contains(it.name) -> {
                    //when hit the backstage property just continue, don't add it to new properties
                }
                else -> newProperties.add(it)
            }
        }
        return newProperties
    }
}


