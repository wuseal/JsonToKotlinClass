package extensions.jose.han;

import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import java.awt.Cursor
import java.awt.Desktop
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URI
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel

/**
 *  @author jose.han
 *  @Date 2019/7/27Ø
 */
object ParcelableAnnotationSupport : Extension() {

    val configKey = "jose.han.add_parcelable_annotatioin_enable"

    override fun createUI(): JPanel {


        val checkBox = JCheckBox("Enable Parcelable Support ").apply {
            isSelected = getConfig(configKey).toBoolean()
            addActionListener {
                setConfig(configKey, isSelected.toString())
            }
        }

        val linkLabel = JLabel("<html><a href='https://github.com/wuseal/JsonToKotlinClass/blob/master/parceable_support_tip.md'>Need Some Config</a></html>")
        linkLabel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                Desktop.getDesktop().browse(URI("https://github.com/wuseal/JsonToKotlinClass/blob/master/parceable_support_tip.md"))
            }

            override fun mouseEntered(e: MouseEvent?) {
                linkLabel.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            }

            override fun mouseExited(e: MouseEvent?) {
                linkLabel.cursor = Cursor.getDefaultCursor()
            }
        })

        return horizontalLinearLayout {
            checkBox()
            linkLabel()
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        return if (getConfig(configKey).toBoolean()) {

            val classAnnotationString1 = "@SuppressLint(\"ParcelCreator\")"
            val classAnnotationString2 = "@Parcelize"

            val classAnnotation1 = Annotation.fromAnnotationString(classAnnotationString1)
            val classAnnotation2 = Annotation.fromAnnotationString(classAnnotationString2)

            return kotlinDataClass.copy(annotations = listOf(classAnnotation1, classAnnotation2), parentClassTemplate = "Parcelable")
        } else {
            kotlinDataClass
        }
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import kotlinx.android.parcel.Parcelize".append("import android.os.Parcelable")

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}
