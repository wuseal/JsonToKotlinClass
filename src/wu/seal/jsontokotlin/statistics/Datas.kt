package wu.seal.jsontokotlin.statistics

import com.google.gson.Gson
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.PLUGIN_VERSION
import wu.seal.jsontokotlin.isTestModel
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by Seal.Wu on 2017/9/27.
 */
data class ConfigInfo(
        val uuid: String = UUID,
        val pluginVersion: String = PLUGIN_VERSION,
        val isPropertiesVar: Boolean = ConfigManager.isPropertiesVar,
        val isCommentOff: Boolean = ConfigManager.isCommentOff,
        val isPropertyNullable: Boolean = ConfigManager.isPropertyNullable,
        val initWithDefaultValue: Boolean = ConfigManager.initWithDefaultValue,
        val targetJsonConverterLib: String = ConfigManager.targetJsonConverterLib.name,
        val timeStamp: String = Date().time.toString(),
        val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())

)


fun main(args: Array<String>) {
    isTestModel = true
    println(Gson().toJson(ConfigInfo()))
}