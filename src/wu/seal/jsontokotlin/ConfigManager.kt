package wu.seal.jsontokotlin

import com.intellij.ide.util.PropertiesComponent

/**
 * ConfigManager
 * main purpose to obtain the detail corresponding config And the entry of modify
 * Created by LENOVO on 2017/9/13.
 */
interface IConfigManager {

    private val IS_PROPERTIES_VAR_KEY: String
        get() = "isPropertiesVar_key"

    private val TARGET_JSON_CONVERTER_LIB_KEY: String
        get() = "target_json_converter_lib_key"

    var isPropertiesVar: Boolean
        get() = PropertiesComponent.getInstance().isTrueValue(IS_PROPERTIES_VAR_KEY)
        set(value) = PropertiesComponent.getInstance().setValue(IS_PROPERTIES_VAR_KEY, value)


    var targetJsonConverterLib: TargetJsonConverter
        get() = TargetJsonConverter.valueOf(PropertiesComponent.getInstance().getValue(TARGET_JSON_CONVERTER_LIB_KEY) ?: TargetJsonConverter.None.name)
        set(value) = PropertiesComponent.getInstance().setValue(TARGET_JSON_CONVERTER_LIB_KEY, value.name)
}


/**
 * This means which Json convert library you are using in you project
 */
enum class TargetJsonConverter {
    None, Gson, FastJson, Jackson
}


object ConfigManager : IConfigManager


fun test() {
    println(ConfigManager.isPropertiesVar)
    ConfigManager.isPropertiesVar = true
    println(ConfigManager.isPropertiesVar)
}