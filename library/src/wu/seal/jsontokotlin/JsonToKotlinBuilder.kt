package wu.seal.jsontokotlin

import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.*
import extensions.xu.rui.PrimitiveTypeNonNullableSupport
import wu.seal.jsontokotlin.test.TestConfig


/**
 * To get Kotlin class code from JSON
 */
class JsonToKotlinBuilder() {

    init {
        TestConfig.apply {

            isTestModel = true
            isCommentOff = false
            isOrderByAlphabetical = true
            isPropertiesVar = false
            targetJsonConvertLib = TargetJsonConverter.None
            propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = true
            enableMinimalAnnotation = false
            indent = 4
            parenClassTemplate = ""
            isKeywordPropertyValid = true
            extensionsConfig = ""
        }
    }

    /**
     * To set property type to `var`, pass true.
     * Default : false
     */
    fun setPropertiesVar(isVar: Boolean): JsonToKotlinBuilder {
        TestConfig.isPropertiesVar = isVar
        return this
    }

    /**
     * To set if the properties can be null or not
     * Default: PropertyTypeStrategy.NotNullable
     */
    fun setPropertyTypeStrategy(strategy: PropertyTypeStrategy): JsonToKotlinBuilder {
        TestConfig.propertyTypeStrategy = strategy
        return this
    }

    /**
     * To set default value.
     * Default : DefaultValueStrategy.AvoidNull
     */
    fun setDefaultValueStrategy(strategy: DefaultValueStrategy): JsonToKotlinBuilder {
        TestConfig.defaultValueStrategy = strategy
        return this
    }

    /**
     * To set JSON decoding/encoding library
     * Default: TargetJsonConverter.None
     */
    fun setAnnotationLib(library: TargetJsonConverter): JsonToKotlinBuilder {
        TestConfig.targetJsonConvertLib = library
        return this
    }

    /**
     * If enabled, value will be commented right to the property
     */
    fun setComment(isEnable: Boolean): JsonToKotlinBuilder {
        TestConfig.isCommentOff = !isEnable
        return this
    }


    /**
     * TODO: More comments need to be added
     */
    fun setOrderByAlphabetic(isOrderByAlphabetic: Boolean): JsonToKotlinBuilder {
        TestConfig.isOrderByAlphabetical = isOrderByAlphabetic
        return this
    }

    fun setInnerClassModel(isInnerClassModel: Boolean): JsonToKotlinBuilder {
        TestConfig.isNestedClassModel = isInnerClassModel
        return this
    }

    fun setMapType(isMapType: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMapType = isMapType
        return this
    }

    fun setCreateAnnotationOnlyWhenNeeded(isOnlyWhenNeeded: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMinimalAnnotation = isOnlyWhenNeeded
        return this
    }

    fun setIndent(indent: Int): JsonToKotlinBuilder {
        TestConfig.indent = indent
        return this
    }

    fun setParentClassTemplate(parentClassTemplate: String): JsonToKotlinBuilder {
        TestConfig.parenClassTemplate = parentClassTemplate
        return this
    }

    fun setKeepAnnotationOnClass(isEnable: Boolean): JsonToKotlinBuilder {
        KeepAnnotationSupport.getTestHelper().setConfig(KeepAnnotationSupport.configKey, isEnable.toString())
        return this
    }

    fun setKeepAnnotationOnClassAndroidX(isEnable: Boolean): JsonToKotlinBuilder {
        KeepAnnotationSupportForAndroidX.getTestHelper().setConfig(KeepAnnotationSupportForAndroidX.configKey, isEnable.toString())
        return this
    }

    fun setKeepAnnotationAndPropertyInSameLine(isEnable: Boolean): JsonToKotlinBuilder {
        PropertyAnnotationLineSupport.getTestHelper().setConfig(PropertyAnnotationLineSupport.configKey, isEnable.toString())
        return this
    }

    fun setParcelableSupport(isEnable: Boolean): JsonToKotlinBuilder {
        ParcelableAnnotationSupport.getTestHelper().setConfig(ParcelableAnnotationSupport.configKey, isEnable.toString())
        return this
    }

    fun setPropertyPrefix(prefix: String?): JsonToKotlinBuilder {
        if (prefix != null) {
            PropertyPrefixSupport.getTestHelper().apply {
                setConfig(PropertyPrefixSupport.prefixKeyEnable, "true")
                setConfig(PropertyPrefixSupport.prefixKey, prefix)
            }
        } else {
            PropertyPrefixSupport.getTestHelper().apply {
                setConfig(PropertyPrefixSupport.prefixKeyEnable, "false")
                setConfig(PropertyPrefixSupport.prefixKey, "")
            }
        }
        return this
    }

    fun setPropertySuffix(suffix: String?): JsonToKotlinBuilder {

        if (suffix != null) {
            PropertySuffixSupport.getTestHelper().apply {
                setConfig(PropertySuffixSupport.suffixKeyEnable, "true")
                setConfig(PropertySuffixSupport.suffixKey, suffix)
            }
        } else {
            PropertySuffixSupport.getTestHelper().apply {
                setConfig(PropertySuffixSupport.suffixKeyEnable, "false")
                setConfig(PropertySuffixSupport.suffixKey, "")
            }
        }
        return this
    }

    fun setClassSuffix(suffix: String?): JsonToKotlinBuilder {
        if (suffix != null) {
            ClassNameSuffixSupport.getTestHelper().apply {
                setConfig(ClassNameSuffixSupport.suffixKeyEnable, "true")
                setConfig(ClassNameSuffixSupport.suffixKey, suffix)
            }
        } else {
            PropertySuffixSupport.getTestHelper().apply {
                setConfig(ClassNameSuffixSupport.suffixKeyEnable, "false")
                setConfig(ClassNameSuffixSupport.suffixKey, "")
            }
        }

        return this
    }

    fun setForceInitDefaultValueWithOriginJsonValue(isEnable: Boolean): JsonToKotlinBuilder {
        ForceInitDefaultValueWithOriginJsonValueSupport.getTestHelper().setConfig(
                ForceInitDefaultValueWithOriginJsonValueSupport.configKey,
                isEnable.toString())
        return this
    }

    fun setForcePrimitiveTypeNonNullable(isEnable: Boolean): JsonToKotlinBuilder {
        PrimitiveTypeNonNullableSupport.getTestHelper().setConfig(PrimitiveTypeNonNullableSupport.configKey, isEnable.toString())
        return this
    }


    fun build(input: String,
              className: String): String {

        return KotlinDataClassCodeMaker(
                KotlinDataClassMaker(
                        className,
                        input
                ).makeKotlinDataClass()
        ).makeKotlinDataClassCode()
    }


}