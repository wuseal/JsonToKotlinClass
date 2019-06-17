package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
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
class AdvancedOtherTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        val parentLayout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        setLayout(parentLayout)

        val boxPanel = JPanel()
        val boxLayout = BoxLayout(boxPanel, BoxLayout.PAGE_AXIS)
        boxPanel.layout = boxLayout
        val jbScrollPane = JBScrollPane(boxPanel)

        jbScrollPane.size = JBDimension(500, 320)
        jbScrollPane.preferredSize = JBDimension(500, 320)
        jbScrollPane.maximumSize = JBDimension(500, 320)
        jbScrollPane.maximumSize = JBDimension(500, 320)

        jbScrollPane.border = null
        addComponentIntoVerticalBoxAlignmentLeft(jbScrollPane)



        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        val enableComment = JBCheckBox("Enable Comment")
        enableComment.isSelected = ConfigManager.isCommentOff.not()
        enableComment.addActionListener { ConfigManager.isCommentOff = enableComment.isSelected.not() }

        val enableOrderByAlphabetical = JBCheckBox("Enable Order By Alphabetical")
        enableOrderByAlphabetical.isSelected = ConfigManager.isOrderByAlphabetical
        enableOrderByAlphabetical.addActionListener { ConfigManager.isOrderByAlphabetical = enableOrderByAlphabetical.isSelected }

        val enableInnerClassModel = JBCheckBox("Enable Inner Class Model")
        enableInnerClassModel.isSelected = ConfigManager.isInnerClassModel
        enableInnerClassModel.addActionListener { ConfigManager.isInnerClassModel = enableInnerClassModel.isSelected }


        val enableMapType = JBCheckBox("Enable Map Type when JSON Field Key Is Primitive Type")
        enableMapType.isSelected = ConfigManager.enableMapType
        enableMapType.addActionListener { ConfigManager.enableMapType = enableMapType.isSelected }

        val enableMinimalAnnotation = JBCheckBox("Only create annotations when needed")
        enableMinimalAnnotation.isSelected = ConfigManager.enableMinimalAnnotation
        enableMinimalAnnotation.addActionListener {
            ConfigManager.enableMinimalAnnotation = enableMinimalAnnotation.isSelected
        }


        val indentJPanel = JPanel()
        indentJPanel.border = JBEmptyBorder(0, 5, 0, 0)
        indentJPanel.maximumSize = JBDimension(400, 30)
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
                val keyChar = e.keyChar
                if (keyChar.toInt() >= KeyEvent.VK_0 && keyChar.toInt() <= KeyEvent.VK_9) {

                } else {
                    e.consume()//ignore the input
                }
            }
        })
        indentField.text = ConfigManager.indent.toString()
        indentJPanel.add(indentField)

        val parentClassPanel = JPanel()
        parentClassPanel.maximumSize = JBDimension(400, 30)
        parentClassPanel.border = JBEmptyBorder(0, 5, 0, 0)
        parentClassPanel.layout = FlowLayout(FlowLayout.LEFT)
        parentClassPanel.add(JBLabel("Parent Class Template: "))
        val parentClassField = JBTextField(20)
        parentClassField.text = ConfigManager.parenClassTemplate
        parentClassField.addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
            }

            override fun focusLost(e: FocusEvent?) {
                ConfigManager.parenClassTemplate = parentClassField.text
            }

        })
        parentClassPanel.add(parentClassField)


        val keywordPropertyValid = JBCheckBox("Make keyword property names valid")
        keywordPropertyValid.isSelected = ConfigManager.keywordPropertyValid
        keywordPropertyValid.addActionListener { ConfigManager.keywordPropertyValid = keywordPropertyValid.isSelected }


        boxPanel.add(Box.createVerticalStrut(JBUI.scale(10)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(enableComment)

        boxPanel.add(Box.createVerticalStrut(JBUI.scale(20)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(enableOrderByAlphabetical)

        boxPanel.add(Box.createVerticalStrut(JBUI.scale(20)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(enableInnerClassModel)

        boxPanel.add(Box.createVerticalStrut(JBUI.scale(20)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(enableMapType)

        boxPanel.add(Box.createVerticalStrut(JBUI.scale(20)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(enableMinimalAnnotation)

        boxPanel.add(Box.createVerticalStrut(JBUI.scale(20)))

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(keywordPropertyValid)

        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(indentJPanel)


        boxPanel.addComponentIntoVerticalBoxAlignmentLeft(parentClassPanel)


        boxPanel.add(Box.createVerticalGlue())


    }

}
