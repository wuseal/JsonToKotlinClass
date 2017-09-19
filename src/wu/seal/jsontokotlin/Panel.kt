package wu.seal.jsontokotlin

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.LayoutManager
import javax.swing.ButtonGroup
import javax.swing.JPanel
import javax.swing.JRadioButton

/**
 * property config panel
 * Created by sealwu on 2017/9/18.
 */
class PropertyPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)


    init {

        setLayout(BorderLayout(5, 5))

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

        add(radioButtonVal, BorderLayout.NORTH)
        add(radioButtonVar, BorderLayout.CENTER)
    }


}


/**
 * Comment Config Panel
 */
class CommentConfigPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        setLayout(BorderLayout(5, 5))

        val radioButtonOpen = JRadioButton("Open")

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

        add(radioButtonOpen, BorderLayout.NORTH)
        add(radioButtonOff, BorderLayout.CENTER)
    }

}

/**
 * Target JsonLib ConfigPanel
 */
class TargetJsonLibConfigPanel(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        setLayout(BorderLayout(5, 5))

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

        add(radioButtonNone, BorderLayout.NORTH)
        add(radioButtonGson, BorderLayout.CENTER)
    }

}

