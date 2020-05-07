package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.*
import javax.swing.JPanel

object PropertySuffixSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKeyEnable = "wu.seal.property_suffix_enable"
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKey = "wu.seal.property_suffix"

    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(suffixKey), getConfig(suffixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(suffixKeyEnable).toBoolean()) {
                    setConfig(suffixKey, text)
                }
            }
            document = NamingConventionDocument(80)
        }

        return jHorizontalLinearLayout {
            jCheckBox("Suffix append after every property: ", getConfig(suffixKeyEnable).toBoolean(), { isSelected ->
                setConfig(suffixKeyEnable, isSelected.toString())
                prefixJField.isEnabled = isSelected
            })
            add(prefixJField)
        }
    }


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {

            return if (getConfig(suffixKeyEnable).toBoolean() && getConfig(suffixKey).isNotEmpty()) {
                val originProperties = kotlinClass.properties
                val newProperties = originProperties.map {
                    val suffix = getConfig(suffixKey)
                    if (it.name.isNotEmpty()) {
                        val newName = it.name + suffix.first().toUpperCase() + suffix.substring(1)
                        it.copy(name = newName)
                    } else it
                }
                kotlinClass.copy(properties = newProperties)
            } else {
                kotlinClass
            }
        } else {
            return kotlinClass
        }
    }
}