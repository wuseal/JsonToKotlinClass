package extensions.jose.han

import extensions.Extension
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

/**
 * @auther jose.han
 * read file head
 */
object FileHeaderSupport : Extension() {

    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "jose.han.file_template"

    override fun createUI(): JPanel {
        return jHorizontalLinearLayout {
            jCheckBox("Enable File Header ", FileHeaderSupport.getConfig(FileHeaderSupport.configKey).toBoolean(),
                    { isSelected -> FileHeaderSupport.setConfig(FileHeaderSupport.configKey, isSelected.toString()) })
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {
            if (FileHeaderSupport.getConfig(FileHeaderSupport.configKey).toBoolean()) {
                val text = ConfigManager.fileHeader
                return kotlinClass.copy(fileHeader = text)
            }
        }

        return kotlinClass
    }
}