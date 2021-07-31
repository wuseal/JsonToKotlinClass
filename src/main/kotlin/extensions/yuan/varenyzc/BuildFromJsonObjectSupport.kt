package extensions.yuan.varenyzc

import extensions.Extension
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import wu.seal.jsontokotlin.ui.jLink
import javax.swing.JPanel

object BuildFromJsonObjectSupport : Extension() {

    const val configKey = "top.varenyzc.build_from_json_enable"

    override fun createUI(): JPanel {
        return jHorizontalLinearLayout {
            jCheckBox(
                "",
                getConfig(configKey).toBoolean(),
                { isSelected -> setConfig(configKey, isSelected.toString()) }
            )
            jLink(
                "Make a static function that can build from JSONObject",
                "https://github.com/wuseal/JsonToKotlinClass/blob/master/doc/build_from_jsonobject_tip.md"
            )
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (getConfig(configKey).toBoolean())
            if (kotlinClass is DataClass) {
                return kotlinClass.copy(codeBuilder = DataClassCodeBuilderForAddingBuildFromJsonObject(kotlinClass.codeBuilder))
            }
        return kotlinClass
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classImportClassString = "import org.json.JSONObject"
        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classImportClassString).append("\n")
        } else {
            originClassImportDeclaration
        }
    }
}