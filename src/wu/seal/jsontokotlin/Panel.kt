package wu.seal.jsontokotlin

import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import com.intellij.util.xml.ui.TextPanel
import java.awt.*
import javax.swing.*
import javax.swing.border.EmptyBorder

/**
 * todo //beautify  interface
 * property config panel
 * Created by seal wu on 2017/9/18.
 */
class PropertyPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)


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


        val line = TextPanel()
        line.maximumSize = JBDimension(480, 1)
        line.minimumSize = JBDimension(480, 1)
        line.background = Color.GRAY

        add(line)

        val nullAbleCheck = JCheckBox("Property type be Nullable(?)")
        if (ConfigManager.isPropertyNullable) {
            nullAbleCheck.isSelected = true
        }

        val initWithDefaultValueCheck = JCheckBox("Init with default value (avoid null)")
        initWithDefaultValueCheck.isSelected = ConfigManager.initWithDefaultValue

        initWithDefaultValueCheck.addChangeListener {
            ConfigManager.initWithDefaultValue = initWithDefaultValueCheck.isSelected
        }

        nullAbleCheck.addChangeListener {
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
class CommentConfigPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)
        setLayout(GridLayout(6, 1, 10, 10))

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
 * Target JsonLib ConfigPanel
 */
class TargetJsonLibConfigPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        setLayout(GridLayout(6, 1, 10, 10))

        val radioButtonNone = JRadioButton("None")

        radioButtonNone.addActionListener {
            ConfigManager.targetJsonConverterLib = TargetJsonConverter.None
        }
        val radioButtonGson = JRadioButton("Gson")
        radioButtonGson.addActionListener {
            ConfigManager.targetJsonConverterLib = TargetJsonConverter.Gson
        }

        if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {

            radioButtonNone.isSelected = true
        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {
            radioButtonGson.isSelected = true
        }

        val buttonGroupProperty = ButtonGroup()
        buttonGroupProperty.add(radioButtonNone)
        buttonGroupProperty.add(radioButtonGson)

        add(radioButtonNone)
        add(radioButtonGson)
    }

}

