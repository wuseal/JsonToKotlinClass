package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.TargetJsonConverter
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
/**
 * JSON Converter Annotation Tab View
 */
class AdvancedAnnotationTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {

    init {

        val customizeAnnotationConfigPanel =
                jVerticalLinearLayout(addToParent = false) {
                    alignLeftComponent {
                        jLabel("Annotation Import Class : ")
                    }
                    jTextAreaInput(ConfigManager.customAnnotationClassImportdeclarationString) {
                        ConfigManager.customAnnotationClassImportdeclarationString = it.text
                    }
                    alignLeftComponent {
                        jLabel("Class Annotation Format:")
                    }
                    jTextAreaInput(ConfigManager.customClassAnnotationFormatString) {
                        ConfigManager.customClassAnnotationFormatString = it.text
                    }
                    alignLeftComponent {
                        jLabel("Property Annotation Format:")
                    }
                    jTextAreaInput(ConfigManager.customPropertyAnnotationFormatString) {
                        ConfigManager.customPropertyAnnotationFormatString = it.text
                    }
                }

        jScrollPanel(JBDimension(500, 300)) {
            jVerticalLinearLayout {
                add(AnnotationsSelectPanel {
                    customizeAnnotationConfigPanel.isVisible = it
                })
                add(customizeAnnotationConfigPanel)
            }
        }.apply {
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS //make sure when open customize annotation panel it's layout view will no change
        }
    }

    /**
     * Target JsonLib ConfigPanel
     */
    class AnnotationsSelectPanel(callBack: (isCustomizeAnnotationSelected: Boolean) -> Unit) : JPanel(BorderLayout(), true) {
        init {
            jGridLayout(5, 2) {
                jButtonGroup {
                    jRadioButton("None", ConfigManager.targetJsonConverterLib == TargetJsonConverter.None, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.None
                    })

                    jRadioButton("None (Camel Case)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.NoneWithCamelCase, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.NoneWithCamelCase
                    })

                    jRadioButton("Gson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Gson
                    })

                    jRadioButton("Jackson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Jackson, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Jackson
                    })

                    jRadioButton("Fastjson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.FastJson, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.FastJson
                    })

                    jRadioButton("MoShi (Reflect)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoShi, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoShi
                    })

                    jRadioButton("MoShi (Codegen)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoshiCodeGen, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoshiCodeGen
                    })
                    jRadioButton("LoganSquare", ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.LoganSquare
                    })
                    jRadioButton("kotlinx.serialization", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Serializable, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Serializable
                    })
                    jRadioButton("Others by customize", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom, {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Custom
                    }) {
                        addChangeListener {
                            callBack(isSelected)
                        }
                    }
                    callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
                }
            }
        }
    }
}
