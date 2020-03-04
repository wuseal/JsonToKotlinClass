package extensions.wu.seal

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.ui.checkBox
import wu.seal.jsontokotlin.ui.horizontalLinearLayout
import javax.swing.JPanel

/**
 * @author Seal.Wu
 * create at 2019/11/03
 * description:
 */
object KeepAnnotationSupportForAndroidX : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "wu.seal.add_keep_annotation_enable_androidx"

    override fun createUI(): JPanel {
        return horizontalLinearLayout {
            checkBox("Add @Keep Annotation On Class (AndroidX)", getConfig(configKey).toBoolean()) { isSelectedAfterClick ->
                setConfig(configKey, isSelectedAfterClick.toString())
            }()
            fillSpace()
        }
    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        return if (getConfig(configKey).toBoolean()) {

            val classAnnotationString = "@Keep"

            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

            val newAnnotations = mutableListOf(classAnnotation).also { it.addAll(kotlinDataClass.annotations) }

            return kotlinDataClass.copy(annotations = newAnnotations)
        } else {
            kotlinDataClass
        }

    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import androidx.annotation.Keep"

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}