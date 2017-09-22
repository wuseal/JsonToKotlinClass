package wu.seal.jsontokotlin

import java.awt.*
import javax.swing.ButtonGroup
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JRadioButton
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

        border = EmptyBorder(10, 10, 10, 10)
        setLayout(GridLayout(5, 1, 10, 10))

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

        add(radioButtonVal)
        add(radioButtonVar)

        val nullableConatainer = JPanel(BorderLayout(10, 10))
        val line = JPanel()
        line.preferredSize = Dimension(500, 1)
        line.background = Color.BLACK
        nullableConatainer.add(line, BorderLayout.NORTH)

        val nullAbleCheck = JCheckBox("Property to be Nullable?")
        if (ConfigManager.isPropertyNullable) {
            nullAbleCheck.isSelected = true
        }

        nullAbleCheck.addChangeListener {
            ConfigManager.isPropertyNullable = nullAbleCheck.isSelected
        }
        nullableConatainer.add(nullAbleCheck, BorderLayout.CENTER)

        add(nullableConatainer)

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

