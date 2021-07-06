package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.builder.CodeBuilderConfig
import wu.seal.jsontokotlin.model.builder.KotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

/**
 * Extension support disable kotlin data class, after enable this, all kotlin data classes will remove 'data' modifier
 */
object DisableDataClassSupport : Extension() {

    const val configKey = "wu.seal.disable_data_class_support"

    override fun createUI(): JPanel {

        return jHorizontalLinearLayout {
            jCheckBox("Disable Kotlin Data Class", getConfig(configKey).toBoolean(), { isSelected -> setConfig(configKey, isSelected.toString()) })
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        CodeBuilderConfig.instance.setConfig(
                KotlinDataClassCodeBuilder.CONF_KOTLIN_IS_DATA_CLASS,
                !getConfig(configKey).toBoolean()
        )
        return kotlinClass
    }
}