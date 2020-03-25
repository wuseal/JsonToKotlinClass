package wu.seal.jsontokotlin.model

import com.intellij.ide.util.PropertiesComponent
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlin.test.TestConfig.isTestModel

/**
 * ConfigManager
 * main purpose to obtain the detail corresponding config And the entry of modify
 * Created by Seal.Wu on 2017/9/13.
 */

interface IConfigManager {

    private val IS_PROPERTIES_VAR_KEY: String
        get() = "isPropertiesVar_key"

    private val TARGET_JSON_CONVERTER_LIB_KEY: String
        get() = "target_json_converter_lib_key"

    private val IS_COMMENT_OFF: String
        get() = "need_comment_key"

    private val IS_ORDER_BY_ALPHABETICAL: String
        get() = "is_order_by_alphabetical"

    private val PROPERTY_TYPE_STRATEGY_KEY: String
        get() = "jsontokotlin_is_property_property_type_strategy_key"

    private val INIT_WITH_DEFAULT_VALUE_KEY: String
        get() = "jsonToKotlin_init_with_default_value_key"

    private val DEFAULT_VALUE_STRATEGY_KEY: String
        get() = "jsonToKotlin_default_value_strategy_key"

    private val USER_UUID_KEY: String
        get() = "jsonToKotlin_user_uuid_value_key"


    private val USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS: String
        get() = "jsonToKotlin_user_custom_json_lib_annotation_import_class"

    private val USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING: String
        get() = "jsontokotlin_user_custom_json_lib_annotation_format_string"

    private val USER_CUSTOM_JSON_LIB_CLASS_ANNOTATION_FORMAT_STRING: String
        get() = "jsontokotlin_user_custom_json_lib_class_annotation_format_string"

    private val INNER_CLASS_MODEL_KEY: String
        get() = "jsontokotlin_inner_class_model_key"


    var isPropertiesVar: Boolean
        get() = if (isTestModel) TestConfig.isPropertiesVar else PropertiesComponent.getInstance().isTrueValue(
                IS_PROPERTIES_VAR_KEY
        )
        set(value) = if (isTestModel) {
            TestConfig.isPropertiesVar = value
        } else {
            PropertiesComponent.getInstance().setValue(IS_PROPERTIES_VAR_KEY, value)
        }


    var isCommentOff: Boolean
        get() = if (isTestModel) TestConfig.isCommentOff else PropertiesComponent.getInstance().getBoolean(
                IS_COMMENT_OFF,
                true
        )
        set(value) = if (isTestModel) {
            TestConfig.isCommentOff = value
        } else {
            PropertiesComponent.getInstance().setValue(IS_COMMENT_OFF, value, true)
        }

    var isOrderByAlphabetical: Boolean
        get() = if (isTestModel) TestConfig.isOrderByAlphabetical else PropertiesComponent.getInstance().getBoolean(
                IS_ORDER_BY_ALPHABETICAL,
                true
        )
        set(value) = if (isTestModel) {
            TestConfig.isOrderByAlphabetical = value
        } else {
            PropertiesComponent.getInstance().setValue(IS_ORDER_BY_ALPHABETICAL, value, true)
        }

    var targetJsonConverterLib: TargetJsonConverter
        get() = if (isTestModel) TestConfig.targetJsonConvertLib else {
            val value = PropertiesComponent.getInstance().getValue(TARGET_JSON_CONVERTER_LIB_KEY)
            //Next step try to keep compatible with 3.5.1 and before version of plugin,
            //Please see : https://github.com/wuseal/JsonToKotlinClass/issues/284
            val compatibleValue = if (value =="Serilizable") "Serializable" else value
            try {
                TargetJsonConverter.valueOf(
                        compatibleValue
                                ?: TargetJsonConverter.None.name
                )
            } catch (e: Exception) {
                TargetJsonConverter.None
            }
        }
        set(value) = if (isTestModel) {
            TestConfig.targetJsonConvertLib = value
        } else {
            PropertiesComponent.getInstance().setValue(TARGET_JSON_CONVERTER_LIB_KEY, value.name)
        }

    var propertyTypeStrategy: PropertyTypeStrategy
        get() = if (isTestModel) TestConfig.propertyTypeStrategy else PropertyTypeStrategy.valueOf(
                PropertiesComponent.getInstance().getValue(
                        PROPERTY_TYPE_STRATEGY_KEY,
                        PropertyTypeStrategy.NotNullable.name
                )
        )
        set(value) = if (isTestModel) {
            TestConfig.propertyTypeStrategy = value
        } else PropertiesComponent.getInstance().setValue(PROPERTY_TYPE_STRATEGY_KEY, value.name)

    var defaultValueStrategy: DefaultValueStrategy
        get() = when {
            isTestModel -> TestConfig.defaultValueStrategy
            // set defaultValueStrategy = AvoidNull when 'init with default value' was selected in version before 3.3.0
            PropertiesComponent.getInstance().getBoolean(INIT_WITH_DEFAULT_VALUE_KEY, false) -> {
                PropertiesComponent.getInstance().unsetValue(INIT_WITH_DEFAULT_VALUE_KEY)
                DefaultValueStrategy.AvoidNull.also {
                    PropertiesComponent.getInstance().setValue(DEFAULT_VALUE_STRATEGY_KEY, it.name)
                }
            }

            else -> DefaultValueStrategy.valueOf(
                    PropertiesComponent.getInstance().getValue(DEFAULT_VALUE_STRATEGY_KEY)
                            ?: DefaultValueStrategy.None.name)
        }
        set(value) = if (isTestModel) {
            TestConfig.defaultValueStrategy = value
        } else {
            PropertiesComponent.getInstance().setValue(DEFAULT_VALUE_STRATEGY_KEY, value.name)
        }

    var userUUID: String
        get() = if (isTestModel) "" else PropertiesComponent.getInstance().getValue(USER_UUID_KEY, "")
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(USER_UUID_KEY, value)
        }

    var customAnnotationClassImportdeclarationString: String
        get() = if (isTestModel) TestConfig.customAnnotaionImportClassString else PropertiesComponent.getInstance().getValue(
                USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS, "import kotlinx.serialization.SerialName\n" +
                "import kotlinx.serialization.Serializable"
        )
        set(value) = if (isTestModel) {
            TestConfig.customAnnotaionImportClassString = value
        } else {
            PropertiesComponent.getInstance().setValue(USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS, value)
        }

    var customPropertyAnnotationFormatString: String
        get() = if (isTestModel) TestConfig.customPropertyAnnotationFormatString else PropertiesComponent.getInstance().getValue(
                USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING,
                "@SerialName(\"%s\")"
        )
        set(value) = if (isTestModel) {
            TestConfig.customPropertyAnnotationFormatString = value
        } else {
            PropertiesComponent.getInstance().setValue(USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING, value)
        }

    var customClassAnnotationFormatString: String
        get() = if (isTestModel) TestConfig.customClassAnnotationFormatString else PropertiesComponent.getInstance().getValue(
                USER_CUSTOM_JSON_LIB_CLASS_ANNOTATION_FORMAT_STRING,
                "@Serializable"
        )
        set(value) = if (isTestModel) {
            TestConfig.customClassAnnotationFormatString = value
        } else {
            PropertiesComponent.getInstance().setValue(USER_CUSTOM_JSON_LIB_CLASS_ANNOTATION_FORMAT_STRING, value)
        }

    var isInnerClassModel: Boolean
        get() = if (isTestModel) TestConfig.isNestedClassModel else PropertiesComponent.getInstance().getBoolean(
                INNER_CLASS_MODEL_KEY,
                false
        )
        set(value) = if (isTestModel) {
            TestConfig.isNestedClassModel = value
        } else {
            PropertiesComponent.getInstance().setValue(INNER_CLASS_MODEL_KEY, value)
        }

}



