package extensions.jose.han;

import com.intellij.ide.BrowserUtil
import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import javax.swing.JCheckBox
import javax.swing.JPanel

/**
 *  @author jose.han
 *  @Date 2019/7/27Ø
 */
object ParcelableAnnotationSupport : Extension() {

    val configKey = "jose.han.add_parcelable_annotatioin_enable"

    override fun createUI(): JPanel {


        val checkBox = JCheckBox("Enable Parcelable Support ").apply {
            isSelected = ParcelableAnnotationSupport.getConfig(ParcelableAnnotationSupport.configKey).toBoolean()
            addActionListener {
                ParcelableAnnotationSupport.setConfig(ParcelableAnnotationSupport.configKey, isSelected.toString())
            }
        }

        return panel {
            row {

                checkBox()
                right {
                    link("Need Some Config") {
                        BrowserUtil.browse("https://github.com/wuseal/JsonToKotlinClass/blob/master/parceable_support_tip.md.md")
                    }
                }
            }
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (ParcelableAnnotationSupport.getConfig(ParcelableAnnotationSupport.configKey).toBoolean()) {

            val classAnnotationString1 = "@SuppressLint(\"ParcelCreator\")"
            val classAnnotationString2 = "@Parcelize"

            val classAnnotation1 = Annotation.fromAnnotationString(classAnnotationString1)
            val classAnnotation2 = Annotation.fromAnnotationString(classAnnotationString2)

            return kotlinDataClass.copy(annotations = listOf(classAnnotation1,classAnnotation2),parentClassTemplate = "Parcelable")
        } else {
            kotlinDataClass
        }
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import kotlinx.android.parcel.Parcelize".append("import android.os.Parcelable")

        return if (ParcelableAnnotationSupport.getConfig(ParcelableAnnotationSupport.configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}
