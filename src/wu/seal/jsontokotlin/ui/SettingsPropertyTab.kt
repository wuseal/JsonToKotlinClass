package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.PropertyTypeStrategy
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
        border = EmptyBorder(bordWidth, bordWidth, 0, bordWidth)

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
        add(Box.createVerticalStrut(JBUI.scale(10)))
        addComponentIntoVerticalBoxAlignmentLeft(radioButtonVal)
        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(radioButtonVar)

        add(Box.createVerticalStrut(JBUI.scale(10)))


        val line = com.intellij.util.xml.ui.TextPanel()
        line.maximumSize = JBDimension(480, 1)
        line.minimumSize = JBDimension(480, 1)
        line.background = Color.GRAY

        add(line)


        val typeLable = JLabel("Type")

        val radioButtonNonNullable = JRadioButton("Non-Nullable")

        radioButtonNonNullable.addActionListener {
            ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        }
        val radioButtonNullable = JRadioButton("Nullable")

        radioButtonNullable.addActionListener {
            ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.Nullable
        }

        val radioButtonAutoDetermineType = JRadioButton("Auto Determine Nullable Or Not From JSON Value")

        radioButtonAutoDetermineType.addActionListener {
            ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot

        }
        if (ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.NotNullable) {

            radioButtonNonNullable.isSelected = true

        } else if (ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable) {

            radioButtonNullable.isSelected = true
        } else {

            radioButtonAutoDetermineType.isSelected = true
        }
        val buttonGroupPropertyType = ButtonGroup()
        buttonGroupPropertyType.add(radioButtonNonNullable)
        buttonGroupPropertyType.add(radioButtonNullable)
        buttonGroupPropertyType.add(radioButtonAutoDetermineType)

        add(Box.createVerticalStrut(JBUI.scale(10)))
        addComponentIntoVerticalBoxAlignmentLeft(typeLable)
        add(Box.createVerticalStrut(JBUI.scale(10)))
        addComponentIntoVerticalBoxAlignmentLeft(radioButtonNonNullable)
        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(radioButtonNullable)

        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(radioButtonAutoDetermineType)

        add(Box.createVerticalStrut(JBUI.scale(10)))

        val lineSecond = com.intellij.util.xml.ui.TextPanel()
        lineSecond.maximumSize = JBDimension(480, 1)
        lineSecond.minimumSize = JBDimension(480, 1)
        lineSecond.background = Color.GRAY

        add(lineSecond)


        val initWithDefaultValueCheck = JCheckBox("Init With Default Value (Avoid Null)")
        initWithDefaultValueCheck.isSelected = ConfigManager.initWithDefaultValue

        initWithDefaultValueCheck.addActionListener {
            ConfigManager.initWithDefaultValue = initWithDefaultValueCheck.isSelected
        }

        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(initWithDefaultValueCheck)
    }
}