package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.NormalClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import javax.swing.JPanel

/**
 * Extension support disable kotlin data class, after enable this, all kotlin data classes will be changed to [NormalClass]
 */
object DisableDataClassSupport : Extension() {

    const val configKey = "wu.seal.disable_data_class_support"

    override fun createUI(): JPanel {

        return horizontalLinearLayout {
            checkBox(
                    "Disable Kotlin Data Class",
                    getConfig(configKey).toBoolean()
            ) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass && getConfig(configKey).toBoolean()) {
            with(kotlinClass) {
                return NormalClass(annotations, name, properties, parentClassTemplate, modifiable)
            }
        } else {
            return kotlinClass
        }
    }
}