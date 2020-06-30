package extensions.nstd

import extensions.Extension
import wu.seal.jsontokotlin.model.builder.CodeBuilderConfig
import wu.seal.jsontokotlin.model.builder.KotlinCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

/**
 * Extension support replace constructor parameters by member variables
 *
 * default:
 *
 *     data class Foo(
 *         @SerializedName("a")
 *         val a: Int = 0 // 1
 *     )
 *
 *
 * after enable this:
 *
 *     data class Foo {
 *         @SerializedName("a")
 *         val a: Int = 0 // 1
 *     }
 *
 * Created by Nstd on 2020/6/29 17:45.
 */
object ReplaceConstructorParametersByMemberVariablesSupport : Extension() {

    const val configKey = "nstd.replace_constructor_parameters_by_member_variables"

    override fun createUI(): JPanel {

        return jHorizontalLinearLayout {
            jCheckBox(
                    "Replace constructor parameters by member variables",
                    getConfig(configKey).toBoolean(),
                    { isSelected -> setConfig(configKey, isSelected.toString()) }
            )
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        CodeBuilderConfig.instance.setConfig(
                KotlinCodeBuilder.BUILD_KEY_IS_USE_CONSTRUCTOR_PARAMETER,
                !getConfig(configKey).toBoolean()
        )
        return kotlinClass
    }
}