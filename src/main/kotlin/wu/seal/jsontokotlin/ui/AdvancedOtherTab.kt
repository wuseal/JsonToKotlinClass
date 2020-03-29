package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.model.ConfigManager
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * others settings tab in config settings dialog
 * Created by Seal.Wu on 2018/2/6.
 */
class AdvancedOtherTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {
    init {
        val content = verticalLinearLayout {
            checkBox("Enable Comment", ConfigManager.isCommentOff.not()) {
                ConfigManager.isCommentOff = it.not()
            }.putAlignLeft()

            checkBox("Enable Order By Alphabetical", ConfigManager.isOrderByAlphabetical) {
                ConfigManager.isOrderByAlphabetical = it
            }.putAlignLeft()

            checkBox("Enable Inner Class Model", ConfigManager.isInnerClassModel) {
                ConfigManager.isInnerClassModel = it
            }.putAlignLeft()

            checkBox("Enable Map Type when JSON Field Key Is Primitive Type", ConfigManager.enableMapType) {
                ConfigManager.enableMapType = it
            }.putAlignLeft()

            checkBox("Only create annotations when needed", ConfigManager.enableMinimalAnnotation) {
                ConfigManager.enableMinimalAnnotation = it
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
