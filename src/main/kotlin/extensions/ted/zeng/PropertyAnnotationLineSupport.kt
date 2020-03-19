package extensions.ted.zeng

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import javax.swing.JPanel

/**
 * Created by ted on 2019-06-13 18:10.
 */
object PropertyAnnotationLineSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "ted.zeng.property_annotation_in_same_line_enable"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            checkBox("Keep Annotation And Property In Same Line", getConfig(configKey).toBoolean()) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            if (getConfig(configKey).toBoolean()) {
                kotlinClass.properties.forEach(Property::letLastAnnotationStayInSameLine)
            }
            kotlinClass
        } else {
            kotlinClass
        }
    }

}