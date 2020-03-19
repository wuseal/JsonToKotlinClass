package extensions.jose.han

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import wu.seal.jsontokotlin.ui.link
import javax.swing.JPanel

/**
 *  @author jose.han
 *  @Date 2019/7/27Ø
 */
object ParcelableAnnotationSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "jose.han.add_parcelable_annotatioin_enable"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            checkBox("Enable Parcelable Support ", getConfig(configKey).toBoolean()) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            link("May Need Some Config", "https://github.com/wuseal/JsonToKotlinClass/blob/master/parceable_support_tip.md")()
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            if (getConfig(configKey).toBoolean()) {

                val classAnnotationString1 = "@SuppressLint(\"ParcelCreator\")"
                val classAnnotationString2 = "@Parcelize"

                val classAnnotation1 = Annotation.fromAnnotationString(classAnnotationString1)
                val classAnnotation2 = Annotation.fromAnnotationString(classAnnotationString2)

                val newAnnotations = mutableListOf(classAnnotation1, classAnnotation2).also { it.addAll(kotlinClass.annotations) }

                return kotlinClass.copy(annotations = newAnnotations, parentClassTemplate = "Parcelable")
            }
        }
        return kotlinClass
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
