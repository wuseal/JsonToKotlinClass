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
import java.awt.BorderLayout
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

    constructor(isDoubleBuffered: Boolean) : this(BorderLayout(), isDoubleBuffered)

    init {

        val content = verticalLinearLayout {

            checkBox("Enable Comment", ConfigManager.isCommentOff.not()) {
                ConfigManager.isCommentOff = it.not()
            }.putAlignLeft()

            checkBox("Enable Order By Alphabetical", ConfigManager.isOrderByAlphabetical) {
                ConfigManager.isOrderByAlphabetical = it
            }.putAlignLeft()

            checkBox("Enable Inner Class Model", ConfigManager.isInnerClassModel) {
                ConfigManager.isInnerClassModel
            }.putAlignLeft()

            checkBox("Enable Map Type when JSON Field Key Is Primitive Type", ConfigManager.enableMapType) {
                ConfigManager.enableMapType = it
            }.putAlignLeft()

            checkBox("Only create annotations when needed", ConfigManager.enableMinimalAnnotation) {
                ConfigManager.enableMinimalAnnotation = it
            }.putAlignLeft()

            checkBox("Make keyword property names valid", ConfigManager.keywordPropertyValid) {
                ConfigManager.keywordPropertyValid = it
            }.putAlignLeft()

            horizontalLinearLayout {
                label("Indent (number of space): ")()
                textInput(ConfigManager.indent.toString()) {
                    val number = try {
                        it.text.toInt()
                    } catch (e: Exception) {
                        it.text = ConfigManager.indent.toString()
                        ConfigManager.indent
                    }
                    ConfigManager.indent = number
                }.apply { columns = 2 }()
            }.putAlignLeft()

            horizontalLinearLayout {
                label("Parent Class Template: ")()
                textInput(ConfigManager.parenClassTemplate) {
                    ConfigManager.parenClassTemplate = it.text
                }.apply {
                    maximumSize = JBDimension(400, 30)
                }()

            }()
        }
        add(content, BorderLayout.CENTER)

    }

}
