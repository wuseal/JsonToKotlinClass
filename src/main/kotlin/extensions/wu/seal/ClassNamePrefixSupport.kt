package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.*
import wu.seal.jsontokotlin.utils.getChildType
import wu.seal.jsontokotlin.utils.getRawType
import javax.swing.JPanel

object ClassNamePrefixSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val prefixKeyEnable = "wu.seal.class_name_prefix_enable"
    @Suppress("MemberVisibilityCanBePrivate")
    const val prefixKey = "wu.seal.class_name_prefix"

    override fun createUI(): JPanel {

        val prefixJField = jTextInput(getConfig(prefixKey), getConfig(prefixKeyEnable).toBoolean()) {
            addFocusLostListener {
                if (getConfig(prefixKeyEnable).toBoolean()) {
                    setConfig(prefixKey, text)
                }
            }
            document = NamingConventionDocument(80)
        }

        return jHorizontalLinearLayout {
            jCheckBox("Prefix append before every class name: ", getConfig(prefixKeyEnable).toBoolean(), { isSelected ->
                setConfig(prefixKeyEnable, isSelected.toString())
                prefixJField.isEnabled = isSelected
            })
            add(prefixJField)
        }
    }


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            val prefix = getConfig(prefixKey)
            return if (getConfig(prefixKeyEnable).toBoolean() && prefix.isNotEmpty()) {
                val standTypes = listOf("Int", "Double", "Long", "String", "Boolean")
                val originName = kotlinClass.name
                val newPropertyTypes =
                        kotlinClass.properties.map {
                            val rawSubType = getChildType(getRawType(it.type))
                            when {
                                it.type.isMapType() -> {
                                    it.type//currently don't support map type
                                }
                                standTypes.contains(rawSubType) -> it.type
                                else -> it.type.replace(rawSubType, prefix + rawSubType.capitalize())
                            }
                        }

                val newPropertyDefaultValues = kotlinClass.properties.map {
                    val rawSubType = getChildType(getRawType(it.type))
                    when {
                        it.value.isEmpty() -> it.value
                        it.type.isMapType() -> {
                            it.value//currently don't support map type
                        }
                        standTypes.contains(rawSubType) -> it.value
                        else -> it.value.replace(rawSubType, prefix + rawSubType.capitalize())
                    }
                }

                val newProperties = kotlinClass.properties.mapIndexed { index, property ->

                    val newType = newPropertyTypes[index]

                    val newValue = newPropertyDefaultValues[index]

                    property.copy(type = newType, value = newValue)
                }
                kotlinClass.copy(name = prefix + originName.capitalize(), properties = newProperties)
            } else {
                kotlinClass
            }

        } else {
            kotlinClass
        }

    }

    private fun String.isMapType(): Boolean {

        return matches(Regex("Map<.+,.+>"))
    }


}

