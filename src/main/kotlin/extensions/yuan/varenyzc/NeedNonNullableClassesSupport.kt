package extensions.yuan.varenyzc

import com.intellij.util.ui.JBDimension
import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.*
import javax.swing.JPanel

object NeedNonNullableClassesSupport : Extension() {

    const val prefixKeyEnable = "top.varenyzc.need_nonnullable_classes_enable"
    const val prefixKey = "top.varenyzc.need_nonnullable_classes"

    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(prefixKey), getConfig(prefixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(prefixKeyEnable).toBoolean()) {
                    setConfig(prefixKey, text)
                }
            }
        }

        return jHorizontalLinearLayout {
            jCheckBox("", getConfig(prefixKeyEnable).toBoolean(), { isSelected ->
                setConfig(prefixKeyEnable, isSelected.toString())
                prefixJField.isEnabled = isSelected
            })
            jLink(
                text = "Classes non-nullable: ",
                linkURL = "https://github.com/wuseal/JsonToKotlinClass/blob/master/doc/classes_non_nullable.md",
                maxSize = JBDimension(160,30)
            )
            add(prefixJField)
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            if (getConfig(prefixKeyEnable).toBoolean()) {
                val list = getConfig(prefixKey).split(',')
                if (list.isEmpty()) {
                    return kotlinClass
                }
                val originProperties = kotlinClass.properties
                val newProperties = originProperties.map {
                    val oldType = it.type
                    if (oldType.isNotEmpty()) {
                        val newType = if (oldType.contains("List<")) {
                            val innerType = oldType.substring(oldType.indexOf('<') + 1, oldType.indexOf('>'))
                            when {
                                list.contains(innerType) -> {
                                    "List<$innerType>?"
                                }
                                list.contains("List") -> {
                                    "List<$innerType?>"
                                }
                                else -> {
                                    "List<$innerType?>?"
                                }
                            }
                        } else {
                            if (list.contains(oldType.replace("?", ""))) {
                                oldType.replace("?","")
                            } else {
                                "${oldType}?"
                            }
                        }
                        it.copy(type = newType)
                    } else {
                        it
                    }
                }
                kotlinClass.copy(properties = newProperties)
            } else {
                kotlinClass
            }
        } else {
            kotlinClass
        }
    }
}