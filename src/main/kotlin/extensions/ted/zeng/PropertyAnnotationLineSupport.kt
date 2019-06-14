package extensions.ted.zeng

import com.intellij.ui.layout.verticalPanel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property
import javax.swing.JCheckBox
import javax.swing.JPanel

/**
 * Created by ted on 2019-06-13 18:10.
 */
object PropertyAnnotationLineSupport : Extension() {

    private const val enable = "ted.zeng.property_annotation_in_same_line_enable"

    override fun createUI(): JPanel {
        val checkBox = JCheckBox("Keep Annotation And Property In Same Line").apply {
            isSelected = getConfig(enable).toBoolean()
            addActionListener {
                setConfig(enable, isSelected.toString())
            }
        }
        return verticalPanel {
            checkBox()
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        if (getConfig(enable).toBoolean()) {
            kotlinDataClass.properties.forEach(Property::letLastAnnotationStayInSameLine)
        }
        return kotlinDataClass
    }

}