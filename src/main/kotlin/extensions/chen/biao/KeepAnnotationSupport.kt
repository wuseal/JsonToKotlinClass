package extensions.chen.biao

import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import javax.swing.JCheckBox
import javax.swing.JPanel

/**
 * @author chenbiao
 * create at 2019/5/16
 * description:
 */
object KeepAnnotationSupport : Extension() {


    val configKey = "chen.biao.add_keep_annotation"

    override fun createUI(): JPanel {

        val checkBox = JCheckBox("add a keep annotation to the class").apply {
            isSelected = getConfig(configKey).toBoolean()
            addActionListener {
                setConfig(configKey, isSelected.toString())
            }
        }

        return panel {
            row {
                checkBox()
            }
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        return if (getConfig(configKey).toBoolean()) {

            val classAnnotationString = "@Keep"

            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

            return kotlinDataClass.copy(annotations = listOf(classAnnotation))
        } else {
            kotlinDataClass
        }

    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import android.support.annotation.Keep"

        return originClassImportDeclaration
                .append(classAnnotationImportClassString)
    }


}















