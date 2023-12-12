package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.*
import javax.swing.JPanel

object BaseClassSupport : Extension() {
    /**
     * Config key can't be private, as it will be accessed from `library` module
     */

    @Suppress("MemberVisibilityCanBePrivate")
    val baseClassSupportEnabledKey = "azk.zero.baseclass_enabled"

    @Suppress("MemberVisibilityCanBePrivate")
    const val baseClassImportKey = "azk.zero.baseclass_import"

    @Suppress("MemberVisibilityCanBePrivate")
    const val baseClassNameKey = "azk.zero.baseclass_name"

    @Suppress("MemberVisibilityCanBePrivate")
    const val baseClassPropertiesKey = "azk.zero.baseclass_properties"

    override fun createUI(): JPanel {
        val classImportField = jTextInput(getConfig(baseClassImportKey), getConfig(baseClassSupportEnabledKey).toBoolean()) {
            addFocusLostListener {
                if (getConfig(baseClassSupportEnabledKey).toBoolean()) {
                    setConfig(baseClassImportKey, text)
                }
            }
            document = ImportConventionDocument()
        }

        val classNameField = jTextInput(getConfig(baseClassNameKey), getConfig(baseClassSupportEnabledKey).toBoolean()) {
            addFocusLostListener {
                if (getConfig(baseClassSupportEnabledKey).toBoolean()) {
                    setConfig(baseClassNameKey, text)
                }
            }
            document = SuperClassConventionDocument(100)
        }

        val classPropertiesField = jTextInput(getConfig(baseClassPropertiesKey), getConfig(baseClassSupportEnabledKey).toBoolean()) {
            addFocusLostListener {
                if (getConfig(baseClassSupportEnabledKey).toBoolean()) {
                    setConfig(baseClassPropertiesKey, text)
                }
            }
            document = PropertyConventionDocument()
        }

        return jVerticalLinearLayout {
            jHorizontalLinearLayout{
                jCheckBox("Base Class Support?", getConfig(baseClassSupportEnabledKey).toBoolean(), { isSelected ->
                    setConfig(baseClassSupportEnabledKey, isSelected.toString())
                    classImportField.isEnabled = isSelected
                    classNameField.isEnabled = isSelected
                    classPropertiesField.isEnabled = isSelected
                })
            }
            jHorizontalLinearLayout {
                jLabel("Base Class Import line")
                add(classImportField)
            }
            jHorizontalLinearLayout {
                jLabel("Base Class Name, used as-is")
                add(classNameField)
            }
            jHorizontalLinearLayout {
                jLabel("Excluded Properties list, comma-separated")
                add(classPropertiesField)
            }
        }
    }
    ;
    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
//        val exclusion = listOf("error", "message", "status_code", "status", "statusCode")
        return if (getConfig(baseClassSupportEnabledKey).toBoolean()) {
            val exclusionNames = getConfig(baseClassPropertiesKey).split(",").map { it.trim() }
            val baseClassName = getConfig(baseClassNameKey)
            if (kotlinClass is DataClass) {
                if (kotlinClass.isTop.not()) return kotlinClass
                val newProperties = kotlinClass.properties.mapNotNull { it.takeIf { it.originName !in exclusionNames } }
                kotlinClass.copy(properties = newProperties, parentClassTemplate = baseClassName)
            } else kotlinClass
        } else {
            kotlinClass
        }
    }

    override fun intercept(originClassImportDeclaration: String): String {

//        val classAnnotationImportClassString = "import com.arena.banglalinkmela.app.data.model.response.base.BaseResponse"
        val classAnnotationImportClassString = getConfig(baseClassImportKey)

        return if (getConfig(baseClassSupportEnabledKey).toBoolean()) {
            originClassImportDeclaration.append("import $classAnnotationImportClassString")
        } else {
            originClassImportDeclaration
        }
    }
}