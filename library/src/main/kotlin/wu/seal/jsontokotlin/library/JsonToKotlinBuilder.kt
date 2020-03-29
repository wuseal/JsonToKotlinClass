package wu.seal.jsontokotlin.library

import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.*
import extensions.xu.rui.PrimitiveTypeNonNullableSupport
import wu.seal.jsontokotlin.*
import wu.seal.jsontokotlin.interceptor.InterceptorManager
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.utils.ClassImportDeclaration


/**
 * To get Kotlin class code from JSON
 */
class JsonToKotlinBuilder {

    private var packageName = ""

    init {
        TestConfig.apply {

            isTestModel = true
            isCommentOff = true
            isOrderByAlphabetical = false
            isPropertiesVar = false
            targetJsonConvertLib = TargetJsonConverter.None
            propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
            defaultValueStrategy = DefaultValueStrategy.None
            isNestedClassModel = false
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
    fun enableVarProperties(isVar: Boolean): JsonToKotlinBuilder {
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

        require(library != TargetJsonConverter.Custom) { "Call #setCustomAnnotation to set custom annotation" }

        TestConfig.targetJsonConvertLib = library
        return this
    }

    fun setCustomAnnotation(
            importClass: String,
            classAnnotationFormat: String,
            propertyAnnotationFormat: String
    ): JsonToKotlinBuilder {
        TestConfig.apply {
            targetJsonConvertLib = TargetJsonConverter.Custom
            customAnnotaionImportClassString = importClass
            customClassAnnotationFormatString = classAnnotationFormat
            customPropertyAnnotationFormatString = propertyAnnotationFormat
        }

        return this
    }

    /**
     * If enabled, value will be commented right to the property
     * Default: false
     */
    fun enableComments(isEnable: Boolean): JsonToKotlinBuilder {
        TestConfig.isCommentOff = !isEnable
        return this
    }


    /**
     * If enabled properties will be ordered in alphabetic order
     * Default : false
     */
    fun enableOrderByAlphabetic(isOrderByAlphabetic: Boolean): JsonToKotlinBuilder {
        TestConfig.isOrderByAlphabetical = isOrderByAlphabetic
        return this
    }


    /**
     * If enabled, classes will be nested with in it's parent class.
     * Default : false
     */
    fun enableInnerClassModel(isInnerClassModel: Boolean): JsonToKotlinBuilder {
        TestConfig.isNestedClassModel = isInnerClassModel
        return this
    }

    /**
     * 
     */
    fun enableMapType(isMapType: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMapType = isMapType
        return this
    }

    fun enableCreateAnnotationOnlyWhenNeeded(isOnlyWhenNeeded: Boolean): JsonToKotlinBuilder {
        TestConfig.enableMinimalAnnotation = isOnlyWhenNeeded
        return this
    }

    /**
     * To set indent in output kotlin code.
     *
     * Default : 4
     * @see <a href="https://github.com/theapache64/JsonToKotlinClass/blob/35fad98bd071feb3ce9493dd1c16866ed1dee7ca/library/src/test/kotlin/wu/seal/jsontokotlin/JsonToKotlinBuilderTest.kt#L799">test case</a>
     */
    fun setIndent(indent: Int): JsonToKotlinBuilder {
        TestConfig.indent = indent
        return this
    }

    fun setParentClassTemplate(parentClassTemplate: String): JsonToKotlinBuilder {
        TestConfig.parenClassTemplate = parentClassTemplate
        return this
    }

    fun enableKeepAnnotationOnClass(isEnable: Boolean): JsonToKotlinBuilder {

        if (isEnable) {
            // disable androidx
            KeepAnnotationSupportForAndroidX.getTestHelper().setConfig(KeepAnnotationSupportForAndroidX.configKey, false.toString())
        }

        KeepAnnotationSupport.getTestHelper().setConfig(KeepAnnotationSupport.configKey, isEnable.toString())
        return this
    }

    fun enableKeepAnnotationOnClassAndroidX(isEnable: Boolean): JsonToKotlinBuilder {

        if (isEnable) {
            // @disable normal one
            KeepAnnotationSupport.getTestHelper().setConfig(KeepAnnotationSupport.configKey, false.toString())
        }

        KeepAnnotationSupportForAndroidX.getTestHelper().setConfig(KeepAnnotationSupportForAndroidX.configKey, isEnable.toString())
        return this
    }

    fun enableAnnotationAndPropertyInSameLine(isEnable: Boolean): JsonToKotlinBuilder {
        PropertyAnnotationLineSupport.getTestHelper().setConfig(PropertyAnnotationLineSupport.configKey, isEnable.toString())
        return this
    }

    fun enableParcelableSupport(isEnable: Boolean): JsonToKotlinBuilder {
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

    fun enableForceInitDefaultValueWithOriginJsonValue(isEnable: Boolean): JsonToKotlinBuilder {
        ForceInitDefaultValueWithOriginJsonValueSupport.getTestHelper().setConfig(
                ForceInitDefaultValueWithOriginJsonValueSupport.configKey,
                isEnable.toString())
        return this
    }

    fun enableForcePrimitiveTypeNonNullable(isEnable: Boolean): JsonToKotlinBuilder {
        PrimitiveTypeNonNullableSupport.getTestHelper().setConfig(PrimitiveTypeNonNullableSupport.configKey, isEnable.toString())
        return this
    }


    fun build(input: String,
              className: String): String {

        val imports = ClassImportDeclaration.applyImportClassDeclarationInterceptors(
                InterceptorManager.getEnabledImportClassDeclarationInterceptors()
        )

        val classCode = KotlinDataClassCodeMaker(
                KotlinDataClassMaker(
                        className,
                        input
                ).makeKotlinDataClass()
        ).makeKotlinDataClassCode()

        val importsAndClassCode = if (imports.isNotBlank()) {
            "$imports\n\n$classCode"
        } else {
            classCode
        }

        return if (packageName.isNotBlank()) {
            "package $packageName\n\n$importsAndClassCode"
        } else {
            importsAndClassCode
        }

    }

    fun setPackageName(packageName: String): JsonToKotlinBuilder {
        this.packageName = packageName
        return this
    }


}