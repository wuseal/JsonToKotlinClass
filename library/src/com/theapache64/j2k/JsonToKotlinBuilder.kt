package com.theapache64.j2k

import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.*
import extensions.xu.rui.PrimitiveTypeNonNullableSupport
import wu.seal.jsontokotlin.*
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
            targetJsonConvertLib = TargetJsonConverter.Gson
            propertyTypeStrategy = PropertyTypeStrategy.NotNullable
            defaultValueStrategy = DefaultValueStrategy.AvoidNull
            isNestedClassModel = true
            customPropertyAnnotationFormatString = "@Optional\n@SerialName(\"%s\")"
            customAnnotaionImportClassString = "import kotlinx.serialization.SerialName\n" +
                    "import kotlinx.serialization.Serializable" + "\n" + "import kotlinx.serialization.Optional"
            customClassAnnotationFormatString = "@Serializable"
            enableMinimalAnnotation = false
            indent = 4
            parenClassTemplate = ""
            isKeywordPropertyValid = true
            extensionsConfig = ""
        }
    }

    /**
     * To set property type to `var`, pass true.
     */
    fun setPropertiesVar(isVar: Boolean): JsonToKotlinBuilder {
        TestConfig.isPropertiesVar = isVar
        return this
    }


    fun setPropertyTypeStrategy(strategy: PropertyTypeStrategy): JsonToKotlinBuilder {
        TestConfig.propertyTypeStrategy = strategy
        return this
    }

    fun setDefaultValueStrategy(strategy: DefaultValueStrategy): JsonToKotlinBuilder {
        TestConfig.defaultValueStrategy = strategy
        return this
    }

    fun setAnnotationLib(library: TargetJsonConverter): JsonToKotlinBuilder {
        TestConfig.targetJsonConvertLib = library
        return this
    }

    fun setComment(isEnable: Boolean): JsonToKotlinBuilder {
        TestConfig.isCommentOff = !isEnable
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


}