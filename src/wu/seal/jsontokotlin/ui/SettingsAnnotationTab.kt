package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
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
        val boxPanel = JPanel()
        boxPanel.layout = BoxLayout(boxPanel, BoxLayout.PAGE_AXIS)
        val bordWidth = JBUI.scale(10)
        boxPanel.border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)
        val subBoxPanel = JPanel()
        subBoxPanel.minimumSize = JBDimension(480, 120)
        val subBoxLayout = BoxLayout(subBoxPanel, BoxLayout.PAGE_AXIS)

        subBoxPanel.layout = subBoxLayout


        addAnnotationClassImportCodeSettingPanel(subBoxPanel)

        addClassAnnotationFormatSettingPanel(subBoxPanel)

        addPropertyAnnotationFormatSettingPanel(subBoxPanel)

        val annotationSelectPanel = TargetJsonLibConfigPanel(true, {
            subBoxPanel.isVisible = it
        })

        annotationSelectPanel.minimumSize = JBDimension(480, 180)
        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationSelectPanel)

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(subBoxPanel)

        subBoxPanel.isVisible = ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom


        setLayout(BoxLayout(this,BoxLayout.PAGE_AXIS))
        border = JBEmptyBorder(0)
        add(JBScrollPane(boxPanel))

    }

    private fun addAnnotationClassImportCodeSettingPanel(subBoxPanel: JPanel): JPanel {
        val annotationImportClass = JPanel(true)
        annotationImportClass.preferredSize = JBDimension(480, 60)
        annotationImportClass.layout = FlowLayout(FlowLayout.LEFT)
        val importClassLable = JBLabel("Annotation Import Class : ")
        annotationImportClass.add(importClassLable)
        val annotationImportClassTextArea = JTextArea(ConfigManager.customAnnotaionImportClassString)
        annotationImportClassTextArea.preferredSize = JBDimension(480, 40)
        annotationImportClassTextArea.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                ConfigManager.customAnnotaionImportClassString = annotationImportClassTextArea.text

            }

            override fun focusGained(e: FocusEvent?) {
            }

        })
        val jbScrollPaneClassFormat = JBScrollPane(annotationImportClassTextArea)
        jbScrollPaneClassFormat.preferredSize = JBDimension(480, 40)
        jbScrollPaneClassFormat.autoscrolls = true
        jbScrollPaneClassFormat.horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        jbScrollPaneClassFormat.verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        annotationImportClass.add(jbScrollPaneClassFormat)
        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationImportClass)
        return annotationImportClass
    }

    private fun addPropertyAnnotationFormatSettingPanel(subBoxPanel: JPanel) {
        val annotationStringPanel = JPanel(true)
        annotationStringPanel.preferredSize = JBDimension(480, 60)
        annotationStringPanel.layout = FlowLayout(FlowLayout.LEFT)
        annotationStringPanel.add(JBLabel("Property Annotation Format: "))
        val annotationFormatField = JTextArea(ConfigManager.customPropertyAnnotationFormatString)

        annotationFormatField.preferredSize = JBDimension(480, 40)
        annotationFormatField.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                ConfigManager.customPropertyAnnotationFormatString = annotationFormatField.text
            }

            override fun focusGained(e: FocusEvent?) {
            }

        })

        val jbScrollPanePropertyFormat = JBScrollPane(annotationFormatField)
        jbScrollPanePropertyFormat.preferredSize = JBDimension(480, 40)
        jbScrollPanePropertyFormat.autoscrolls = true
        jbScrollPanePropertyFormat.horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        jbScrollPanePropertyFormat.verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        annotationStringPanel.add(jbScrollPanePropertyFormat)

        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationStringPanel)
    }

    private fun addClassAnnotationFormatSettingPanel(subBoxPanel: JPanel) {
        val annotationClasssFormatStringPanel = JPanel(true)
        annotationClasssFormatStringPanel.preferredSize = JBDimension(480, 60)
        annotationClasssFormatStringPanel.layout = FlowLayout(FlowLayout.LEFT)
        annotationClasssFormatStringPanel.add(JBLabel("Class Annotation Format: "))
        val annotationClassFormatField = JTextArea(ConfigManager.customPropertyAnnotationFormatString)

        annotationClassFormatField.preferredSize = JBDimension(480, 40)
        annotationClassFormatField.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                ConfigManager.customClassAnnotationFormatString = annotationClassFormatField.text
            }

            override fun focusGained(e: FocusEvent?) {
            }

        })

        val jbScrollPaneClassAnnotationFormat = JBScrollPane(annotationClassFormatField)
        jbScrollPaneClassAnnotationFormat.preferredSize = JBDimension(480, 40)
        jbScrollPaneClassAnnotationFormat.autoscrolls = true
        jbScrollPaneClassAnnotationFormat.horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        jbScrollPaneClassAnnotationFormat.verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        annotationClasssFormatStringPanel.add(jbScrollPaneClassAnnotationFormat)

        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationClasssFormatStringPanel)
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
