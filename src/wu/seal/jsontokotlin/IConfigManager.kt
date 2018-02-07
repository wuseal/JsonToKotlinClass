package wu.seal.jsontokotlin

import com.intellij.ide.util.PropertiesComponent
import wu.seal.jsontokotlin.supporter.GsonSupporter
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

    private val IS_PROPERTY_NULLABLE_KEY: String
        get() = "jsonToKotlin_is_property_nullable_key"


    private val INIT_WITH_DEFAULT_VALUE_KEY: String
        get() = "jsonToKotlin_init_with_default_value_key"


    private val USER_UUID_KEY: String
        get() = "jsonToKotlin_user_uuid_value_key"


    private val USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS: String
        get() = "jsonToKotlin_user_custom_json_lib_annotation_import_class"

    private val USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING: String
        get() = "jsontokotlin_user_custom_json_lib_annotation_format_string"

    private val INNER_CLASS_MODEL_KEY: String
        get() = "jsontokotlin_inner_class_model_key"


    var isPropertiesVar: Boolean
        get() = if (isTestModel) TestConfig.isPropertiesVar else PropertiesComponent.getInstance().isTrueValue(IS_PROPERTIES_VAR_KEY)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(IS_PROPERTIES_VAR_KEY, value)
        }


    var isCommentOff: Boolean
        get() = if (isTestModel) TestConfig.isCommentOff else PropertiesComponent.getInstance().getBoolean(IS_COMMENT_OFF, true)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(IS_COMMENT_OFF, value, true)
        }


    var targetJsonConverterLib: TargetJsonConverter
        get() = if (isTestModel) TestConfig.targetJsonConvertLib else TargetJsonConverter.valueOf(PropertiesComponent.getInstance().getValue(TARGET_JSON_CONVERTER_LIB_KEY) ?: TargetJsonConverter.None.name)
        set(value) = if (isTestModel) {
            TestConfig.targetJsonConvertLib = value
        } else {
            PropertiesComponent.getInstance().setValue(TARGET_JSON_CONVERTER_LIB_KEY, value.name)
        }

    var isPropertyNullable: Boolean
        get() = if (isTestModel) TestConfig.isPropertyNullable else PropertiesComponent.getInstance().isTrueValue(IS_PROPERTY_NULLABLE_KEY)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(IS_PROPERTY_NULLABLE_KEY, value)
        }


    var initWithDefaultValue: Boolean
        get() = if (isTestModel) TestConfig.initWithDefaultValue else PropertiesComponent.getInstance().getBoolean(INIT_WITH_DEFAULT_VALUE_KEY)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(INIT_WITH_DEFAULT_VALUE_KEY, value)
        }

    var userUUID: String
        get() = if (isTestModel) "" else PropertiesComponent.getInstance().getValue(USER_UUID_KEY, "")
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(USER_UUID_KEY, value)
        }

    var customAnnotaionImportClassString: String
        get() = if (isTestModel) GsonSupporter.annotationImportClassString else PropertiesComponent.getInstance().getValue(USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS, GsonSupporter.annotationImportClassString)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(USER_CUSTOM_JSON_LIB_ANNOTATION_IMPORT_CLASS, value)
        }

    var customAnnotaionFormatString: String
        get() = if (isTestModel) GsonSupporter.anotaionOnProperty else PropertiesComponent.getInstance().getValue(USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING, GsonSupporter.anotaionOnProperty)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(USER_CUSTOM_JSON_LIB_ANNOTATION_FORMAT_STRING, value)
        }
    var isInnerClassModel: Boolean
        get() = if (isTestModel) TestConfig.isInnerClassModel else PropertiesComponent.getInstance().getBoolean(INNER_CLASS_MODEL_KEY, false)
        set(value) = if (isTestModel) {
        } else {
            PropertiesComponent.getInstance().setValue(INNER_CLASS_MODEL_KEY, value)
        }

}



