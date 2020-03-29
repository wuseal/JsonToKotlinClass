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
        val customizeAnnotationConfigPanel = verticalLinearLayout {
            label("Annotation Import Class : ").putAlignLeft()
            textAreaInput(ConfigManager.customAnnotationClassImportdeclarationString) {
                ConfigManager.customAnnotationClassImportdeclarationString = it.text
            }()
            label("Class Annotation Format: ").putAlignLeft()
            textAreaInput(ConfigManager.customClassAnnotationFormatString) {
                ConfigManager.customClassAnnotationFormatString = it.text
            }()
            label("Property Annotation Format: ").putAlignLeft()
            textAreaInput(ConfigManager.customPropertyAnnotationFormatString) {
                ConfigManager.customPropertyAnnotationFormatString = it.text
            }()
        }
        val content = verticalLinearLayout {
            AnnotationsSelectPanel(true) {
                customizeAnnotationConfigPanel.isVisible = it
            }()
            customizeAnnotationConfigPanel()
        }

        val scrollPanel = scrollPanel(JBDimension(500, 300)) {
            content
        }.apply {
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS //make sure when open customize annotation panel it's layout view will no change
        }
        add(scrollPanel, BorderLayout.CENTER)
    }

    /**
     * Target JsonLib ConfigPanel
     */
    class AnnotationsSelectPanel(isDoubleBuffered: Boolean, callBack: (isCustomizeAnnotationSelected: Boolean) -> Unit) : JPanel(BorderLayout(), isDoubleBuffered) {
        init {
            val content = gridLayout(5, 2) {
                radioGroup {
                    radioButton("None", ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.None
                    }().addToGroup()

                    radioButton("None (Camel Case)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.NoneWithCamelCase) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.NoneWithCamelCase
                    }().addToGroup()

                    radioButton("Gson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Gson
                    }().addToGroup()

                    radioButton("Jackson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Jackson) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Jackson
                    }().addToGroup()

                    radioButton("Fastjson", ConfigManager.targetJsonConverterLib == TargetJsonConverter.FastJson) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.FastJson
                    }().addToGroup()

                    radioButton("MoShi(Reflect)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoShi) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoShi
                    }().addToGroup()

                    radioButton("MoShi (Codegen)", ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoshiCodeGen) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoshiCodeGen
                    }().addToGroup()

                    radioButton("LoganSquare", ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.LoganSquare
                    }().addToGroup()

                    radioButton("kotlinx.serialization", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Serializable) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Serializable
                    }().addToGroup()

                    val customizeAnnotationRadioButton = radioButton("Others by customize", ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom) {
                        ConfigManager.targetJsonConverterLib = TargetJsonConverter.Custom
                    }().addToGroup()

                    onRadioButtonSelectListener = {
                        callBack(it == customizeAnnotationRadioButton)
                    }
                    callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
                }
            }
            add(content, BorderLayout.CENTER)
        }
    }
}
