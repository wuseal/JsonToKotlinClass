package wu.seal.jsontokotlinclass.server.utils

import extensions.Extension
import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.ForceInitDefaultValueWithOriginJsonValueSupport
import extensions.wu.seal.KeepAnnotationSupportForAndroidX
import extensions.xu.rui.PrimitiveTypeNonNullableSupport
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlinclass.server.data.entities.Hit
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest

/**
 * To convert GenerateRequest to Hit
 */
fun GenerateRequest.toHit(
        client: String
): Hit {

    /**
     * TODO: Variable name length must be reduced
     */
    val hit = Hit()
    hit.client = client
    hit.className = className
    hit.annotationLib = parseAnnotationLib(annotationLib)
    hit.defaultValueStrategy = parseDefaultValueStrategy(defaultValueStrategy)
    hit.propertyTypeStrategy = parsePropertyTypeStrategy(propertyTypeStrategy)
    hit.indent = indent ?: TestConfig.indent
    hit.isCommentsEnabled = isCommentsEnabled ?: !TestConfig.isCommentOff
    hit.isCreateAnnotationOnlyWhenNeededEnabled = isCreateAnnotationOnlyWhenNeededEnabled
            ?: TestConfig.enableMinimalAnnotation
    hit.isEnableVarProperties = isEnableVarProperties ?: TestConfig.isPropertiesVar

    hit.isForceInitDefaultValueWithOriginJsonValueEnabled = isForceInitDefaultValueWithOriginJsonValueEnabled
            ?: getDefaultValue(ForceInitDefaultValueWithOriginJsonValueSupport, ForceInitDefaultValueWithOriginJsonValueSupport.configKey)
    hit.isForcePrimitiveTypeNonNullableEnabled = isForcePrimitiveTypeNonNullableEnabled
            ?: getDefaultValue(PrimitiveTypeNonNullableSupport, PrimitiveTypeNonNullableSupport.configKey)
    hit.isInnerClassModelEnabled = isInnerClassModelEnabled ?: TestConfig.isNestedClassModel
    hit.isKeepAnnotationOnClassAndroidxEnabled = isKeepAnnotationOnClassAndroidXEnabled ?: getDefaultValue(
            KeepAnnotationSupportForAndroidX, KeepAnnotationSupportForAndroidX.configKey
    )
    hit.isKeepAnnotationOnClassEnabled = isKeepAnnotationOnClassEnabled ?: getDefaultValue(
            KeepAnnotationSupport, KeepAnnotationSupport.configKey
    )
    hit.isMapTypeEnabled = isMapTypeEnabled ?: TestConfig.enableMapType
    hit.isOrderByAlphabeticEnabled = isOrderByAlphabeticEnabled ?: TestConfig.isOrderByAlphabetical
    hit.isParcelableSupportEnabled = isParcelableSupportEnabled ?: getDefaultValue(
            ParcelableAnnotationSupport, ParcelableAnnotationSupport.configKey
    )
    hit.isPropertyAndAnnotationInSameLineEnabled = isPropertyAndAnnotationInSameLineEnabled ?: getDefaultValue(
            PropertyAnnotationLineSupport, PropertyAnnotationLineSupport.configKey
    )

    hit.packageName = packageName
    hit.parentClassTemplate = parentClassTemplate
    hit.propertyPrefix = propertyPrefix
    hit.propertySuffix = propertySuffix
    hit.classSuffix = classSuffix
    return hit
}

/*fun getDefaultValue(extension: Extension, key: String): Boolean {
    return extension.getTestHelper().getConfig(key) == "true"
}*/

fun parsePropertyTypeStrategy(propertyTypeStrategy: String?): String {
    return when (propertyTypeStrategy ?: TestConfig.propertyTypeStrategy.name) {
        PropertyTypeStrategy.Nullable.name -> Hit.PTS_NULLABLE
        PropertyTypeStrategy.NotNullable.name -> Hit.PTS_NOT_NULLABLE
        PropertyTypeStrategy.AutoDeterMineNullableOrNot.name -> Hit.PTS_AUTO_DETERMINE_NULLABLE_OR_NOT
        else -> throw IllegalArgumentException("Undefined property ")
    }
}

fun parseDefaultValueStrategy(defaultValueStrategy: String?): String {
    return when (defaultValueStrategy ?: TestConfig.defaultValueStrategy.name) {
        DefaultValueStrategy.None.name -> Hit.DVS_NONE
        DefaultValueStrategy.AvoidNull.name -> Hit.DVS_AVOID_NULL
        DefaultValueStrategy.AllowNull.name -> Hit.DVS_ALLOW_NULL
        else -> throw IllegalArgumentException("Undefined DVS : $defaultValueStrategy")
    }
}

fun parseAnnotationLib(annotationLib: String?): String {
    return when (annotationLib ?: TestConfig.targetJsonConvertLib.name) {
        TargetJsonConverter.None.name -> Hit.A_LIB_NONE
        TargetJsonConverter.NoneWithCamelCase.name -> Hit.A_LIB_NONE_CC
        TargetJsonConverter.Gson.name -> Hit.A_LIB_GSON
        TargetJsonConverter.FastJson.name -> Hit.A_LIB_FAST_JSON
        TargetJsonConverter.Jackson.name -> Hit.A_LIB_JACKSON
        TargetJsonConverter.MoShi.name -> Hit.A_LIB_MOSHI
        TargetJsonConverter.LoganSquare.name -> Hit.A_LIB_LOGAN_SQUARE
        TargetJsonConverter.MoshiCodeGen.name -> Hit.A_LIB_MOSHI_CODE_GEN
        TargetJsonConverter.Serializable.name -> Hit.A_LIB_SERIALIZABLE
        TargetJsonConverter.Custom.name -> Hit.A_LIB_CUSTOM
        else -> throw IllegalArgumentException("Undefined annotation library $annotationLib")
    }
}
