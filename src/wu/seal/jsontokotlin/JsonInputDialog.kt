package wu.seal.jsontokotlin

import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBDimension
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.text.JTextComponent

/**
 * Dialog widget relative
 * Created by Seal.wu on 2017/9/21.
 */

/**
 * Json input Dialog
 */
class JsonInputDialog(project: Project) : Messages.InputDialog(project, "Please input the Json Data", "Input Json", Messages.getInformationIcon(), "", object : InputValidator {
    override fun checkInput(inputString: String): Boolean {
        try {
            val jsonElement = JsonParser().parse(inputString)
            return jsonElement.isJsonObject || jsonElement.isJsonArray
        } catch (e: JsonSyntaxException) {
            return false
        }

    }

    override fun canClose(inputString: String): Boolean {
        return true
    }
}) {
    override fun createMessagePanel(): JPanel {
        val messagePanel = JPanel(BorderLayout())
        if (myMessage != null) {
            val textComponent = createTextComponent()
            messagePanel.add(textComponent, BorderLayout.NORTH)
        }

        myField = createTextFieldComponent()


        messagePanel.add(createScrollableTextComponent(), BorderLayout.CENTER)
        val settingButton = JButton("Config Settings")
        settingButton.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                ConfigSettingDialog(false).show()
            }
        })
        val settingContainer = JPanel()
        val boxLayout = BoxLayout(settingContainer, BoxLayout.LINE_AXIS)
        settingContainer.layout = boxLayout
        settingButton.horizontalAlignment = SwingConstants.RIGHT
        settingContainer.add(settingButton)
        messagePanel.add(settingContainer, BorderLayout.SOUTH)
        return messagePanel
    }

    override fun createTextFieldComponent(): JTextComponent {
        val jTextArea = JTextArea(15, 100)
        jTextArea.minimumSize = JBDimension(800, 500)
        jTextArea.maximumSize = JBDimension(1000, 700)
        jTextArea.lineWrap = true
        jTextArea.wrapStyleWord = true
        jTextArea.autoscrolls = true
        return jTextArea
    }


    protected fun createScrollableTextComponent(): JComponent {
        return JBScrollPane(myField)
    }
}