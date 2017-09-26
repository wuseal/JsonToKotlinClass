package wu.seal.jsontokotlin

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
        border = EmptyBorder(10, 10, 10, 10)

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


        val line = JPanel()
        line.maximumSize = Dimension(1000, 1)
        line.minimumSize = Dimension(500, 1)
        line.preferredSize = Dimension(800, 1)
        line.background = Color.BLACK

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

        border = EmptyBorder(10, 10, 10, 10)

        setLayout(GridLayout(5, 1, 10, 10))

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

        border = EmptyBorder(10, 10, 10, 10)

        setLayout(GridLayout(5, 1, 10, 10))

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

