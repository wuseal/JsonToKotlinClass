package extensions.wu.seal

import com.google.gson.annotations.Expose
import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.addSelectListener
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

object AddGsonExposeAnnotation : Extension() {

    @Expose
    val config_key = "wu.seal.add_gson_expose_annotation"

    override fun createUI(): JPanel {
        return jHorizontalLinearLayout {
            jCheckBox("Add Gson Expose Annotation", config_key.booleanConfigValue) {
                addSelectListener { setConfig(config_key, it.toString()) }
            }
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (config_key.booleanConfigValue) {
            if (kotlinClass is DataClass) {
                val newProperties = kotlinClass.properties.map {
                    val newAnnotations = it.annotations + Annotation.fromAnnotationString("@Expose")
                    it.copy(annotations = newAnnotations)
                }
                kotlinClass.copy(properties = newProperties)
            } else kotlinClass
        } else kotlinClass
    }

    override fun intercept(originClassImportDeclaration: String): String {
        return if (config_key.booleanConfigValue) {
            originClassImportDeclaration.append("import com.google.gson.annotations.Expose")
        } else originClassImportDeclaration
    }
}