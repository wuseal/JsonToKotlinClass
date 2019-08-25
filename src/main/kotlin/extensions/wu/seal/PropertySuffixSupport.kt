package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.NamingConventionDocument
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.ui.textInput
import javax.swing.JPanel

object PropertySuffixSupport : Extension() {

    private const val suffixKeyEnable = "wu.seal.property_suffix_enable"
    private const val suffixKey = "wu.seal.property_suffix"
    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            val prefixJField = textInput(getConfig(suffixKey), getConfig(suffixKeyEnable).toBoolean()) {
                if (getConfig(suffixKeyEnable).toBoolean()) {
                    setConfig(suffixKey, it.text)
                }
            }.also{
                it.document = NamingConventionDocument(80)
            }
            checkBox("Suffix append after every property: ", getConfig(suffixKeyEnable).toBoolean()) { isSelectedAfterClick ->
                setConfig(suffixKeyEnable, isSelectedAfterClick.toString())
                prefixJField.isEnabled = isSelectedAfterClick
            }()
            prefixJField()
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(suffixKeyEnable).toBoolean() && getConfig(suffixKey).isNotEmpty()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val suffix = getConfig(suffixKey)
                if (it.name.isNotEmpty()) {
                    val newName = it.name + suffix.first().toUpperCase() + suffix.substring(1)
                    it.copy(name = newName)
                } else it
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }
    }
}