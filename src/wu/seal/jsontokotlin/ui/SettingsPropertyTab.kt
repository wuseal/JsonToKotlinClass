package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.Color
import java.awt.FlowLayout
import java.awt.LayoutManager
import javax.swing.*
import javax.swing.border.EmptyBorder

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
class SettingsPropertyTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

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


        val line = com.intellij.util.xml.ui.TextPanel()
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

        initWithDefaultValueCheck.addActionListener {
            ConfigManager.initWithDefaultValue = initWithDefaultValueCheck.isSelected
        }

        nullAbleCheck.addActionListener {
            ConfigManager.isPropertyNullable = nullAbleCheck.isSelected
        }

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(initWithDefaultValueCheck)
        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(nullAbleCheck)


    }
}