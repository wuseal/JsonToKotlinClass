import extensions.chen.biao.KeepAnnotationSupport
import wu.seal.jsontokotlin.*
import wu.seal.jsontokotlin.test.TestConfig

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

    fun setKeepAnnotationOnClass(isInclude: Boolean): JsonToKotlinBuilder {
        KeepAnnotationSupport.getTestHelper().setConfig("chen.biao.add_keep_annotation_enable", "true")
        return this
    }


}