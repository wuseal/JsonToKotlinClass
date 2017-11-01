package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.addComponentIntoVerticalBoxAlignmentLeft
import javax.swing.*
import javax.swing.border.EmptyBorder


/**
 * todo //beautify  interface
 * property config panel
 * Created by seal wu on 2017/9/18.
 */
class PropertyPanel(layout: java.awt.LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: java.awt.LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(java.awt.FlowLayout(), isDoubleBuffered)


    init {


        val boxLayout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        setLayout(boxLayout)
        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        val keywordLable = JLabel("Keyword")

        val radioButtonVal = JRadioButton("Val")

        radioButtonVal.addActionListener {
            ConfigManager.isPropertiesVar = false
        }
        val radioButtonVar = JRadioButton("Var")

        radioButtonVar.addActionListener {
            ConfigManager.isPropertiesVar = true
        }

        if (ConfigManager.isPropertiesVar) {

            radioButtonVar.isSelected = true
        } else {
            radioButtonVal.isSelected = true
        }
        val buttonGroupProperty = ButtonGroup()
        buttonGroupProperty.add(radioButtonVal)
        buttonGroupProperty.add(radioButtonVar)

        addComponentIntoVerticalBoxAlignmentLeft(keywordLable)
        add(Box.createVerticalStrut(20))
        addComponentIntoVerticalBoxAlignmentLeft(radioButtonVal)
        add(Box.createVerticalStrut(20))

        addComponentIntoVerticalBoxAlignmentLeft(radioButtonVar)

        add(Box.createVerticalStrut(20))


        val line = com.intellij.util.xml.ui.TextPanel()
        line.maximumSize = JBDimension(480, 1)
        line.minimumSize = JBDimension(480, 1)
        line.background = java.awt.Color.GRAY

        add(line)

        val nullAbleCheck = JCheckBox("Property type be Nullable(?)")
        if (ConfigManager.isPropertyNullable) {
            nullAbleCheck.isSelected = true
        }

        val initWithDefaultValueCheck = JCheckBox("Init with default value (avoid null)")
        initWithDefaultValueCheck.isSelected = ConfigManager.initWithDefaultValue

        initWithDefaultValueCheck.addActionListener {
            ConfigManager.initWithDefaultValue = initWithDefaultValueCheck.isSelected
        }

        nullAbleCheck.addActionListener {
            ConfigManager.isPropertyNullable = nullAbleCheck.isSelected
        }

        add(Box.createVerticalStrut(20))

        addComponentIntoVerticalBoxAlignmentLeft(initWithDefaultValueCheck)
        add(Box.createVerticalStrut(20))

        addComponentIntoVerticalBoxAlignmentLeft(nullAbleCheck)


    }
}


/**
 * Comment Config Panel
 */
class CommentConfigPanel(layout: java.awt.LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: java.awt.LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(java.awt.FlowLayout(), isDoubleBuffered)

    init {

        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)
        setLayout(java.awt.GridLayout(6, 1, 10, 10))

        val radioButtonOpen = JRadioButton("On")

        radioButtonOpen.addActionListener {
            ConfigManager.isCommentOff = false
        }
        val radioButtonOff = JRadioButton("Off")
        radioButtonOff.addActionListener {
            ConfigManager.isCommentOff = true
        }

        if (ConfigManager.isCommentOff) {

            radioButtonOff.isSelected = true
        } else {
            radioButtonOpen.isSelected = true
        }

        val buttonGroupProperty = ButtonGroup()
        buttonGroupProperty.add(radioButtonOpen)
        buttonGroupProperty.add(radioButtonOff)

        add(radioButtonOpen)
        add(radioButtonOff)
    }

}

/**
 * Target JsonLib ConfigPanel container
 */
class TargetJsonLibConfigPanelContainer(layout: java.awt.LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: java.awt.LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(java.awt.FlowLayout(), isDoubleBuffered)

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
        annotationStringPanel.layout = java.awt.FlowLayout(java.awt.FlowLayout.LEFT)
        annotationStringPanel.add(JBLabel("Property Annotation Format: "))
        val annotationFormatField = JTextField(ConfigManager.customAnnotaionFormatString)
        val fieldDefaultFont = annotationFormatField.font
        annotationFormatField.addFocusListener(object : java.awt.event.FocusListener {
            override fun focusLost(e: java.awt.event.FocusEvent?) {
                ConfigManager.customAnnotaionFormatString = annotationFormatField.text
            }

            override fun focusGained(e: java.awt.event.FocusEvent?) {
            }

        })
        annotationStringPanel.add(annotationFormatField, java.awt.FlowLayout.CENTER)
        subBoxPanel.addComponentIntoVerticalBoxAlignmentLeft(annotationStringPanel)

        val annotationImportClass = JPanel(true)
        annotationImportClass.layout = java.awt.FlowLayout(java.awt.FlowLayout.LEFT)
        val importClassLable = JBLabel("Property Annotation Import Class : ")
        importClassLable.border = JBEmptyBorder(3,0,3,0)
        annotationImportClass.add(importClassLable)
        val annotationImportClassTextArea = JTextArea(ConfigManager.customAnnotaionImportClassString)
        annotationImportClassTextArea.font = fieldDefaultFont
        annotationImportClassTextArea.preferredSize = JBDimension(480, 30)
        annotationImportClassTextArea.addFocusListener(object : java.awt.event.FocusListener {
            override fun focusLost(e: java.awt.event.FocusEvent?) {
                ConfigManager.customAnnotaionImportClassString = annotationImportClassTextArea.text

            }

            override fun focusGained(e: java.awt.event.FocusEvent?) {
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
}

/**
 * Target JsonLib ConfigPanel
 */
class TargetJsonLibConfigPanel(layout: java.awt.LayoutManager?, isDoubleBuffered: Boolean, callBack: (selected: Boolean) -> Unit) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: java.awt.LayoutManager?, callBack: (selected: Boolean) -> Unit) : this(layout, false, callBack)

    constructor(isDoubleBuffered: Boolean, callBack: (selected: Boolean) -> Unit) : this(java.awt.FlowLayout(), isDoubleBuffered, callBack)

    init {
        setLayout(java.awt.GridLayout(4, 2, 10, 10))

        val radioButtonNone = JRadioButton("None")
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
        buttonGroupProperty.add(radioButtonGson)
        buttonGroupProperty.add(radioButtonJackson)
        buttonGroupProperty.add(radioButtonFastjson)
        buttonGroupProperty.add(radioButtonMoShi)
        buttonGroupProperty.add(radioButtonLoganSquare)
        buttonGroupProperty.add(radioButtonCustom)

        add(radioButtonNone)
        add(radioButtonGson)
        add(radioButtonJackson)
        add(radioButtonFastjson)
        add(radioButtonMoShi)
        add(radioButtonLoganSquare)
        add(radioButtonCustom)
    }

}

