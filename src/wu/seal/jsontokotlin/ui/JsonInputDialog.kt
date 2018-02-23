package wu.seal.jsontokotlin.ui

import com.google.gson.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import wu.seal.jsontokotlin.feedback.FormatJSONAction
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.text.JTextComponent

/**
 * Dialog widget relative
 * Created by Seal.wu on 2017/9/21.
 */


class MyInputValidator : InputValidator {

    lateinit var classNameField: JTextField
    override fun checkInput(inputString: String): Boolean {
        try {
            val classNameLegal = classNameField.text.trim().isNotBlank()
            val jsonElement = JsonParser().parse(inputString)

            return (jsonElement.isJsonObject || jsonElement.isJsonArray) && classNameLegal
        } catch (e: JsonSyntaxException) {
            return false
        }

    }

    override fun canClose(inputString: String): Boolean {
        return true
    }
}

val myInputValidator = MyInputValidator()

/**
 * Json input Dialog
 */
class JsonInputDialog(private val classsName: String, project: Project) : Messages.InputDialog(project, "Please input the class name and JSON String for generating Kotlin data class", "Make Kotlin Data Class", Messages.getInformationIcon(), "", myInputValidator) {

    private lateinit var classNameInput: JTextField

    private val prettyGson: Gson = GsonBuilder().setPrettyPrinting().create()

    init {
        setOKButtonText("Make")
        classNameInput.text = classsName
    }

    override fun createMessagePanel(): JPanel {
        val messagePanel = JPanel(BorderLayout())
        if (myMessage != null) {
            val textComponent = createTextComponent()
            messagePanel.add(textComponent, BorderLayout.NORTH)
        }
        myField = createTextFieldComponent()


        val classNameInputContainer = createLinearLayoutVertical()
        val classNameTitle = JBLabel("Class Name: ")
        classNameTitle.border = JBEmptyBorder(5, 0, 5, 0)
        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameTitle)
        classNameInput = JTextField()
        classNameInput.preferredSize = JBDimension(400, 40)
        myInputValidator.classNameField = classNameInput

        classNameInput.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent?) {
                okAction.isEnabled = myInputValidator.checkInput(myField.text)
            }
        })

        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameInput)
        classNameInputContainer.preferredSize = JBDimension(500, 56)


        val createScrollableTextComponent = createMyScrollableTextComponent()
        val jsonInputContainer = createLinearLayoutVertical()
        jsonInputContainer.preferredSize = JBDimension(700, 400)
        jsonInputContainer.border = JBEmptyBorder(5, 0, 5, 5)
        val jsonTitle = JBLabel("JSON Text:")
        jsonTitle.border = JBEmptyBorder(5, 0, 5, 0)
        jsonInputContainer.addComponentIntoVerticalBoxAlignmentLeft(jsonTitle)
        jsonInputContainer.addComponentIntoVerticalBoxAlignmentLeft(createScrollableTextComponent)


        val centerContainer = JPanel()
        val centerBoxLayout = BoxLayout(centerContainer, BoxLayout.PAGE_AXIS)
        centerContainer.layout = centerBoxLayout
        centerContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameInputContainer)
        centerContainer.addComponentIntoVerticalBoxAlignmentLeft(jsonInputContainer)
        messagePanel.add(centerContainer, BorderLayout.CENTER)
        val settingButton = JButton("Settings")
        settingButton.horizontalAlignment = SwingConstants.CENTER
        settingButton.addActionListener(object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                SettingsDialog(false).show()
            }
        })
        val formatButton = JButton("Format")
        formatButton.horizontalAlignment = SwingConstants.CENTER
        formatButton.addActionListener(object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                handleFormatJSONString()
            }

        })
        val settingContainer = JPanel()
        settingContainer.border = JBEmptyBorder(0, 5, 5, 7)
        val boxLayout = BoxLayout(settingContainer, BoxLayout.LINE_AXIS)
        settingContainer.layout = boxLayout
        settingContainer.add(settingButton)
        settingContainer.add(Box.createHorizontalGlue())
        settingContainer.add(formatButton)
        messagePanel.add(settingContainer, BorderLayout.SOUTH)

        return messagePanel
    }

    override fun createTextFieldComponent(): JTextComponent {
        val jTextArea = JTextArea(15, 50)
        jTextArea.minimumSize = JBDimension(750, 400)
//        jTextArea.lineWrap = true
//        jTextArea.wrapStyleWord = true
//        jTextArea.autoscrolls = true
        return jTextArea
    }


    protected fun createMyScrollableTextComponent(): JComponent {
        val jbScrollPane = JBScrollPane(myField)
        jbScrollPane.preferredSize = JBDimension(700, 350)
        jbScrollPane.autoscrolls = true
        jbScrollPane.horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        jbScrollPane.verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        return jbScrollPane
    }

    fun getClassName(): String {
        if (exitCode == 0) {
            return classNameInput.text.trim()
        }
        return ""
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        if (classNameInput.text?.isEmpty() ?: true) {
            return classNameInput
        } else {
            return myField
        }
    }

    fun handleFormatJSONString() {
        val currentText = myField.text ?: ""
        if (currentText.isNotEmpty()) {
            try {
                val jsonElement = prettyGson.fromJson<JsonElement>(currentText, JsonElement::class.java)
                val formatJSON = prettyGson.toJson(jsonElement)
                myField.text = formatJSON
            } catch (e: Exception) {
            }
        }

        feedBackFormatJSONActionInfo()

    }

    private fun feedBackFormatJSONActionInfo() {
        Thread { sendActionInfo(prettyGson.toJson(FormatJSONAction())) }.start()
    }
}


fun createLinearLayoutVertical(): JPanel {
    val container = JPanel()
    val boxLayout = BoxLayout(container, BoxLayout.PAGE_AXIS)
    container.layout = boxLayout
    return container
}