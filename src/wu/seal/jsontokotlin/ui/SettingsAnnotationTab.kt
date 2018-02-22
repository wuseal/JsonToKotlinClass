package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.LayoutManager
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.*
import javax.swing.border.EmptyBorder

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
/**
 * JSON Converter Annotation Tab View
 */
class SettingsAnnotationTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {
        val boxLayout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        setLayout(boxLayout)
        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        val subBoxPanel = JPanel()
        subBoxPanel.preferredSize = JBDimension(480, 90)
        subBoxPanel.maximumSize = JBDimension(480, 90)
        val subBoxLayout = BoxLayout(subBoxPanel, BoxLayout.PAGE_AXIS)

        subBoxPanel.layout = subBoxLayout
        val annotationStringPanel = JPanel(true)
        annotationStringPanel.maximumSize = JBDimension(480, 30)
        annotationStringPanel.layout = FlowLayout(FlowLayout.LEFT)
        annotationStringPanel.add(JBLabel("Property Annotation Format: "))
        val annotationFormatField = JTextField(ConfigManager.customAnnotaionFormatString)
        val fieldDefaultFont = annotationFormatField.font
        annotationFormatField.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                ConfigManager.customAnnotaionFormatString = annotationFormatField.text
            }

            override fun focusGained(e: FocusEvent?) {
            }

        })
        annotationStringPanel.add(annotationFormatField, FlowLayout.CENTER)
        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationStringPanel)

        val annotationImportClass = JPanel(true)
        annotationImportClass.layout = FlowLayout(FlowLayout.LEFT)
        val importClassLable = JBLabel("Property Annotation Import Class : ")
        importClassLable.border = JBEmptyBorder(3, 0, 3, 0)
        annotationImportClass.add(importClassLable)
        val annotationImportClassTextArea = JTextArea(ConfigManager.customAnnotaionImportClassString)
        annotationImportClassTextArea.font = fieldDefaultFont
        annotationImportClassTextArea.preferredSize = JBDimension(480, 40)
        annotationImportClassTextArea.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                ConfigManager.customAnnotaionImportClassString = annotationImportClassTextArea.text

            }

            override fun focusGained(e: FocusEvent?) {
            }

        })
        annotationImportClass.add(annotationImportClassTextArea)
        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationImportClass)

        addComponentIntoVerticalBoxAlignmentLeft(TargetJsonLibConfigPanel(true, {
            annotationStringPanel.isVisible = it
            annotationImportClass.isVisible = it
        }))

        addComponentIntoVerticalBoxAlignmentLeft(subBoxPanel)

        annotationStringPanel.isVisible = ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom
        annotationImportClass.isVisible = ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom

    }


    /**
     * Target JsonLib ConfigPanel
     */
    class TargetJsonLibConfigPanel(layout: LayoutManager?, isDoubleBuffered: Boolean, callBack: (selected: Boolean) -> Unit) : JPanel(layout, isDoubleBuffered) {

        constructor(layout: LayoutManager?, callBack: (selected: Boolean) -> Unit) : this(layout, false, callBack)

        constructor(isDoubleBuffered: Boolean, callBack: (selected: Boolean) -> Unit) : this(FlowLayout(), isDoubleBuffered, callBack)

        init {
            setLayout(GridLayout(4, 2, 10, 10))

            val radioButtonNone = JRadioButton("None")
            val radioButtonNoneWithCamelCase = JRadioButton("None (Camel Case)")
            val radioButtonGson = JRadioButton("Gson")
            val radioButtonJackson = JRadioButton("Jackson")
            val radioButtonFastjson = JRadioButton("Fastjson")
            val radioButtonMoShi = JRadioButton("MoShi")
            val radioButtonLoganSquare = JRadioButton("LoganSquare")
            val radioButtonCustom = JRadioButton("Others by customize")

            radioButtonNone.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.None
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)

            }
            radioButtonNoneWithCamelCase.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.NoneWithCamelCase
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)

            }
            radioButtonGson.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.Gson
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)

            }
            radioButtonJackson.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.Jackson
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
            }
            radioButtonFastjson.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.FastJson
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
            }

            radioButtonMoShi.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.MoShi
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
            }

            radioButtonLoganSquare.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.LoganSquare
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
            }
            radioButtonCustom.addActionListener {
                ConfigManager.targetJsonConverterLib = TargetJsonConverter.Custom
                callBack(ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom)
            }

            if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {

                radioButtonNone.isSelected = true

            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.NoneWithCamelCase) {

                radioButtonNoneWithCamelCase.isSelected = true

            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {

                radioButtonGson.isSelected = true

            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Jackson) {

                radioButtonJackson.isSelected = true
            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.FastJson) {

                radioButtonFastjson.isSelected = true
            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare) {

                radioButtonLoganSquare.isSelected = true
            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoShi) {

                radioButtonMoShi.isSelected = true
            } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom) {

                radioButtonCustom.isSelected = true

            }

            val buttonGroupProperty = ButtonGroup()
            buttonGroupProperty.add(radioButtonNone)
            buttonGroupProperty.add(radioButtonNoneWithCamelCase)
            buttonGroupProperty.add(radioButtonGson)
            buttonGroupProperty.add(radioButtonJackson)
            buttonGroupProperty.add(radioButtonFastjson)
            buttonGroupProperty.add(radioButtonMoShi)
            buttonGroupProperty.add(radioButtonLoganSquare)
            buttonGroupProperty.add(radioButtonCustom)

            add(radioButtonNone)
            add(radioButtonNoneWithCamelCase)
            add(radioButtonGson)
            add(radioButtonJackson)
            add(radioButtonFastjson)
            add(radioButtonMoShi)
            add(radioButtonLoganSquare)
            add(radioButtonCustom)
        }

    }
}
