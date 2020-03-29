package extensions.xu.rui

import extensions.Extension
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.codeelements.getDefaultValue
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


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (getConfig(configKey).toBoolean().not()) {
            return kotlinClass
        }

        if (kotlinClass is DataClass) {

            val primitiveTypeNonNullableProperties = kotlinClass.properties.map {
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

            return kotlinClass.copy(properties = primitiveTypeNonNullableProperties)
        } else {
            return kotlinClass
        }
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