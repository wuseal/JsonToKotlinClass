package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.codeelements.getDefaultValue
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.utils.TYPE_STRING
import javax.swing.JPanel

/**
 * This extension make the default value of data class property to be json value
 * This extension should be put at last
 * Created by Seal.Wu on 2019-11-09
 */
object ForceInitDefaultValueWithOriginJsonValueSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "wu.seal.force_init_default_value_with_origin_json_value"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            (checkBox(
                "Force init Default Value With Origin Json Value",
                getConfig(configKey).toBoolean()
            ) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            })()
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {

            return if (getConfig(configKey).toBoolean()) {
                val newP = kotlinClass.properties.map {
                    val newV = if (it.originJsonValue.isNullOrBlank()) getDefaultValue(it.type) else {
                        if (it.type == TYPE_STRING) {
                            """"${it.originJsonValue}""""
                        } else {
                            it.originJsonValue
                        }
                    }
                    it.copy(value = newV)
                }
                kotlinClass.copy(properties = newP)
            } else {
                kotlinClass
            }
        } else {
            return  kotlinClass
        }
    }
}