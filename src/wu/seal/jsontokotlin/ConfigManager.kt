package wu.seal.jsontokotlin

import com.intellij.ide.util.PropertiesComponent

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


    var isPropertiesVar: Boolean
        get() = if (isTestModel) TestConfig.isPropertiesVar else PropertiesComponent.getInstance().isTrueValue(IS_PROPERTIES_VAR_KEY)
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(IS_PROPERTIES_VAR_KEY, value)


    var isCommentOff: Boolean
        get() = if (isTestModel) TestConfig.isCommentOff else PropertiesComponent.getInstance().isTrueValue(IS_COMMENT_OFF)
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(IS_COMMENT_OFF, value)


    var targetJsonConverterLib: TargetJsonConverter
        get() = if (isTestModel) TestConfig.targetJsonConvertLib else TargetJsonConverter.valueOf(PropertiesComponent.getInstance().getValue(TARGET_JSON_CONVERTER_LIB_KEY) ?: TargetJsonConverter.None.name)
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(TARGET_JSON_CONVERTER_LIB_KEY, value.name)

    var isPropertyNullable: Boolean
        get() = if (isTestModel) TestConfig.isPropertyNullable else PropertiesComponent.getInstance().isTrueValue(IS_PROPERTY_NULLABLE_KEY)
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(IS_PROPERTY_NULLABLE_KEY, value)


    var initWithDefaultValue: Boolean
        get() = if (isTestModel) TestConfig.initWithDefaultValue else PropertiesComponent.getInstance().getBoolean(INIT_WITH_DEFAULT_VALUE_KEY)
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(INIT_WITH_DEFAULT_VALUE_KEY, value)

    var userUUID: String
        get() = if (isTestModel) "" else PropertiesComponent.getInstance().getValue(USER_UUID_KEY, "")
        set(value) = if (isTestModel) {
        } else PropertiesComponent.getInstance().setValue(USER_UUID_KEY, value)

}

/**
 * This means which Json convert library you are using in you project
 */
enum class TargetJsonConverter {
    None, Gson, FastJson, Jackson
}


object ConfigManager : IConfigManager

/**
 * If it is in test model
 */
var isTestModel = false

/**
 * config for test unit
 */
object TestConfig {
    var isCommentOff = false
    var isPropertiesVar = false
    var targetJsonConvertLib = TargetJsonConverter.Gson
    var isPropertyNullable = true
    var initWithDefaultValue = true
}