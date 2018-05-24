package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.FlowLayout
import java.awt.LayoutManager
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * others settings tab in config settings dialog
 * Created by Seal.Wu on 2018/2/6.
 */
class SettingsOtherTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {
        val boxLayout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        setLayout(boxLayout)
        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        val enableComment = JBCheckBox("Enable Comment")
        enableComment.isSelected = ConfigManager.isCommentOff.not()
        enableComment.addActionListener { ConfigManager.isCommentOff = enableComment.isSelected.not() }


        val enableInnerClassModel = JBCheckBox("Enable Inner Class Model")
        enableInnerClassModel.isSelected = ConfigManager.isInnerClassModel
        enableInnerClassModel.addActionListener { ConfigManager.isInnerClassModel = enableInnerClassModel.isSelected }


        val enableMapType = JBCheckBox("Enable Map Type when JSON Field Key Is Primitive Type")
        enableMapType.isSelected = ConfigManager.enableMapType
        enableMapType.addActionListener { ConfigManager.enableMapType = enableMapType.isSelected }


        val enableAutoReformat = JBCheckBox("Auto reformatting generated code according to code style. (Note that the Indent option bellow would be ignored.)")
        enableAutoReformat.isSelected = ConfigManager.enableAutoReformat
        enableAutoReformat.addActionListener { ConfigManager.enableAutoReformat = enableAutoReformat.isSelected }

        val indentJPanel = JPanel()
        indentJPanel.layout = FlowLayout(FlowLayout.LEFT)
        indentJPanel.add(JBLabel("Indent (number of space): "))
        val indentField = JBTextField(2)
        indentField.addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
            }

            override fun focusLost(e: FocusEvent?) {
                val number = try {
                    indentField.text.toInt()
                } catch (e: Exception) {
                    indentField.text = ConfigManager.indent.toString()
                    ConfigManager.indent
                }
                ConfigManager.indent = number
            }

        })
        indentField.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent) {
                val keyChar = e.keyChar;
                if (keyChar.toInt() >= KeyEvent.VK_0 && keyChar.toInt() <= KeyEvent.VK_9) {

                } else {
                    e.consume()//ignore the input
                }
            }
        })
        indentField.text = ConfigManager.indent.toString()
        indentJPanel.add(indentField)

        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(enableComment)

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(enableInnerClassModel)

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(enableMapType)

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(enableAutoReformat)

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(indentJPanel)
    }

}