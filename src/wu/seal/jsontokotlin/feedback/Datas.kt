package wu.seal.jsontokotlin.feedback

import wu.seal.jsontokotlin.ConfigManager
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
        val isInnerClassMode:Boolean = ConfigManager.isInnerClassModel,
        val timeStamp: String = Date().time.toString(),
        val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())

)
