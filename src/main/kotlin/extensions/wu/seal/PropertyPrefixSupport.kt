package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.ui.textInput
import javax.swing.JPanel

object PropertyPrefixSupport : Extension() {

    private const val prefixKeyEnable = "wu.seal.property_prefix_enable"
    private const val prefixKey = "wu.seal.property_prefix"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            val prefixJField = textInput(getConfig(prefixKey), getConfig(prefixKeyEnable).toBoolean()) { it ->
                if (getConfig(prefixKeyEnable).toBoolean()) {
                    setConfig(prefixKey, it.text)
                }
            }
            checkBox("Prefix append before every property: ", getConfig(prefixKeyEnable).toBoolean()) { isSelectedAfterClick ->
                setConfig(prefixKeyEnable, isSelectedAfterClick.toString())
                prefixJField.isEnabled = isSelectedAfterClick
            }()
            prefixJField()
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(prefixKeyEnable).toBoolean() && getConfig(prefixKey).isNotEmpty()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val prefix = getConfig(prefixKey)
                if (it.name.isNotEmpty()) {
                    val newName = prefix + it.name.first().toUpperCase() + it.name.substring(1)
                    it.copy(name = newName)
                } else it
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }

    }
}