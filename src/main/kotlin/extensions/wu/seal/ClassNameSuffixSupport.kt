package extensions.wu.seal

import com.intellij.ui.layout.panel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.getChildType
import wu.seal.jsontokotlin.utils.getRawType
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField

object ClassNameSuffixSupport : Extension() {

    private const val suffixKeyEnable = "wu.seal.class_name_suffix_enable"
    private const val suffixKey = "wu.seal.class_name_suffix"

    override fun createUI(): JPanel {
        val prefixJField = JTextField().apply {
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

            minimumSize = JBDimension(150, 25)

            isEnabled = getConfig(suffixKeyEnable).toBoolean()

        }

        val checkBox = JCheckBox("Suffix append after every class name: ").apply {
            isSelected = getConfig(suffixKeyEnable).toBoolean()
            addActionListener {
                setConfig(suffixKeyEnable, isSelected.toString())
                prefixJField.isEnabled = isSelected
            }
        }

        return panel {
            row {
                checkBox()
                prefixJField()
            }
        }.apply {
            border = JBEmptyBorder(6, 0, 0, 0)
        }
    }


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val suffix = getConfig(suffixKey)

        return if (getConfig(suffixKeyEnable).toBoolean() && suffix.isNotEmpty()) {
            val standTypes = listOf("Int", "Double", "Long", "String", "Boolean")
            val originName = kotlinDataClass.name
            val newPropertyTypes =
                kotlinDataClass.properties.map {
                    val rawSubType = getChildType(getRawType(it.type))
                    when {
                        it.type.isMapType() -> {
                            it.type//currently don't support map type
                        }
                        standTypes.contains(rawSubType) -> it.type
                        else -> it.type.replace(rawSubType, rawSubType + suffix)
                    }
                }

            val newPropertyDefaultValues = kotlinDataClass.properties.map {
                val rawSubType = getChildType(getRawType(it.type))
                when {
                    it.value.isEmpty()-> it.value
                    it.type.isMapType() -> {
                        it.value//currently don't support map type
                    }
                    standTypes.contains(rawSubType) -> it.value
                    else -> it.value.replace(rawSubType, rawSubType + suffix)
                }
            }

            val newProperties = kotlinDataClass.properties.mapIndexed { index, property ->

                val newType = newPropertyTypes[index]

                val newValue = newPropertyDefaultValues[index]

                property.copy(type = newType,value = newValue)
            }


            kotlinDataClass.copy(name = originName + suffix, properties = newProperties)

        } else {
            kotlinDataClass
        }

    }

    private fun String.isMapType(): Boolean {

        return matches(Regex("Map<.+,.+>"))
    }


}

