package extensions.wu.seal

import com.intellij.ui.layout.panel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import extensions.Extension
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField

object PropertySuffixSupport : Extension() {

    private const val suffixKeyEnable = "wu.seal.property_suffix_enable"
    private const val suffixKey = "wu.seal.property_suffix"
    override fun createUI(): JPanel {
        val suffixJField = JTextField().apply {
            text = getConfig(suffixKey)

            addFocusListener(object : FocusListener {
                override fun focusGained(e: FocusEvent?) {
                }
                override fun focusLost(e: FocusEvent?) {
                    if (getConfig(suffixKeyEnable).toBoolean()) {
                        setConfig(suffixKey, text)
                    }
                }
            })
            isEnabled = getConfig(suffixKeyEnable).toBoolean()
            minimumSize = JBDimension(150, 25)
        }

        val checkBox = JCheckBox("Suffix append after every property: ").apply {
            isSelected = getConfig(suffixKeyEnable).toBoolean()
            addActionListener {
                setConfig(suffixKeyEnable, isSelected.toString())
                suffixJField.isEnabled = isSelected
            }
        }

        return panel {
            row {
                checkBox()
                suffixJField()
            }
        }.apply {
            border = JBEmptyBorder(6,0,0,0)
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(suffixKeyEnable).toBoolean()) {
            val originProperties = kotlinDataClass.properties
            val newProperties = originProperties.map {
                val suffix = getConfig(suffixKey)
                val newName = it.name + suffix.first().toUpperCase() +suffix.substring(1)
                it.copy(name = newName)
            }
            kotlinDataClass.copy(properties = newProperties)
        } else {
            kotlinDataClass
        }
    }
}