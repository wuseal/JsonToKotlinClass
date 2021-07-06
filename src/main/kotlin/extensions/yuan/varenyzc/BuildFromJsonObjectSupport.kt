package extensions.yuan.varenyzc

import extensions.Extension
import wu.seal.jsontokotlin.model.builder.CodeBuilderConfig
import wu.seal.jsontokotlin.model.builder.KotlinDataClassCodeBuilder
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
                "Make a static function that can build from JSONObject",
                getConfig(configKey).toBoolean(),
                { isSelected -> setConfig(configKey, isSelected.toString()) }
            )
            add(jLink("Know about this extension", "https://github.com/wuseal/JsonToKotlinClass/blob/master/build_from_jsonobject_tip.md"))
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        CodeBuilderConfig.instance.setConfig(
            KotlinDataClassCodeBuilder.CONF_BUILD_FROM_JSON_OBJECT,
            getConfig(configKey).toBoolean()
        )
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