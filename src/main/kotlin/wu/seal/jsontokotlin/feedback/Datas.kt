package wu.seal.jsontokotlin.feedback

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
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
        val isOrderByAlphabetical: Boolean = ConfigManager.isOrderByAlphabetical,
        val propertyTypeStrategy: String = ConfigManager.propertyTypeStrategy.name,
        val defaultValueStrategy: DefaultValueStrategy = ConfigManager.defaultValueStrategy,
        val targetJsonConverterLib: String = ConfigManager.targetJsonConverterLib.name,
        val isInnerClassMode: Boolean = ConfigManager.isInnerClassModel,
        val customAnnotationImportClassString: String = ConfigManager.customAnnotationClassImportdeclarationString,
        val customClassAnnotationFormatString: String = ConfigManager.customClassAnnotationFormatString,
        val customPropertyAnnotationFormatString: String = ConfigManager.customPropertyAnnotationFormatString,
        val enableMapType: Boolean = ConfigManager.enableMapType,
        val enableMinimalAnnotation: Boolean = ConfigManager.enableMinimalAnnotation,
        val parenClassTemplate: String = ConfigManager.parenClassTemplate,
        val extensionsConfig: String = ConfigManager.extensionsConfig,
        val timeStamp: String = Date().time.toString(),
        val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())

)
