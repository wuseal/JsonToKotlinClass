package extensions.xu.rui

import extensions.Extension
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.utils.NULLABLE_PRIMITIVE_TYPES
import wu.seal.jsontokotlin.utils.getNonNullPrimitiveType
import javax.swing.JPanel

object PrimitiveTypeNonNullableSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "xu.rui.force_primitive_type_non-nullable"


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        if (getConfig(configKey).toBoolean().not()) {
            return kotlinDataClass
        }

        val primitiveTypeNonNullableProperties = kotlinDataClass.properties.map {
            if (it.type in NULLABLE_PRIMITIVE_TYPES) {
                val newType = getNonNullPrimitiveType(it.type)
                if (ConfigManager.defaultValueStrategy != DefaultValueStrategy.None) {
                    it.copy(type = newType, value = getDefaultValue(newType))
                } else {
                    it.copy(type = newType)
                }
            } else {
                it
            }
        }

        return kotlinDataClass.copy(properties = primitiveTypeNonNullableProperties)
    }

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            (checkBox("Force Primitive Type Property Non-Nullable", getConfig(configKey).toBoolean()) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            })()
            fillSpace()
        }
    }

}