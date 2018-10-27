package wu.seal.jsontokotlin.ui

import com.google.gson.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import wu.seal.jsontokotlin.feedback.FormatJSONAction
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.net.MalformedURLException
import java.net.URL
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.text.JTextComponent

/**
 * Dialog widget relative
 * Created by Seal.wu on 2017/9/21.
 */


class MyInputValidator : InputValidator {
    lateinit var jbTabbedPane: JBTabbedPane
    lateinit var httpUrlInput: JTextComponent
    lateinit var fileChooser: JFileChooser
    lateinit var classNameField: JTextComponent

    override fun checkInput(inputString: String): Boolean {
        val classNameBlank = classNameField.text.trim().isBlank()
        if (classNameBlank) {
            return false
        }
        return when (jbTabbedPane.selectedIndex) {
            0 -> isJsonStringValid(inputString)
            1 -> isUrlValid(httpUrlInput.text.trim())
            2 -> true
            else -> false
        }
    }

    override fun canClose(inputString: String) = true

    private fun isJsonStringValid(string: String) = try {
        val jsonElement = JsonParser().parse(string)
        (jsonElement.isJsonObject || jsonElement.isJsonArray)
    } catch (e: JsonSyntaxException) {
        false
    }

    private fun isUrlValid(urlString: String) = try {
        URL(urlString)
        true
    } catch (e: MalformedURLException) {
        false
    }
}

val myInputValidator = MyInputValidator()

/**
 * Json input Dialog
 */
class JsonInputDialog(classsName: String, project: Project) : Messages.InputDialog(project, "Please input the class name and JSON String for generating Kotlin data class", "Make Kotlin Data Class Code", Messages.getInformationIcon(), "", myInputValidator) {

    private lateinit var jbTabbedPane: JBTabbedPane
    private lateinit var classNameInput: JTextComponent
    private lateinit var httpUrlInput: JTextComponent
    private lateinit var fileChooser: JFileChooser

    private val prettyGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    init {
        setOKButtonText("Make")
        classNameInput.text = classsName
    }

    override fun createMessagePanel(): JPanel {
        val messagePanel = JPanel(BorderLayout())
        if (myMessage != null) {
            messagePanel.add(createTextComponent(), BorderLayout.NORTH)
        }
        myField = createTextFieldComponent()

        val classNameInputContainer = createLinearLayoutVertical()
        val classNameTitle = JBLabel("Class Name: ")
        classNameTitle.border = JBEmptyBorder(5, 0, 5, 0)
        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameTitle)
        classNameInput = JTextField()
        classNameInput.preferredSize = JBDimension(400, 40)
        classNameInput.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent) {
                val keyChar = e.keyChar
                if (keyChar == '˚') {
                    e.consume()
                }
            }
        })
        myInputValidator.classNameField = classNameInput

        classNameInput.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent?) = revalidate()
        })

        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameInput)
        classNameInputContainer.preferredSize = JBDimension(500, 56)


        val jsonTextComponent = createMyScrollableTextComponent(myField)
        val jsonInputContainer = createLinearLayoutVertical()
        jsonInputContainer.preferredSize = JBDimension(700, 400)
        jsonInputContainer.border = JBEmptyBorder(5, 0, 5, 5)
        jsonInputContainer.addComponentIntoVerticalBoxAlignmentLeft(jsonTextComponent)

        jbTabbedPane = JBTabbedPane(SwingConstants.TOP)
        jbTabbedPane.addChangeListener { revalidate() }
        myInputValidator.jbTabbedPane = jbTabbedPane
        jbTabbedPane.add("From Json", jsonInputContainer)

        val httpInputContainer = JPanel().apply {
            layout = BorderLayout()
        }
        httpUrlInput = JTextField()
        httpUrlInput.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent?) = revalidate()
        })
        myInputValidator.httpUrlInput = httpUrlInput
        httpInputContainer.add(httpUrlInput, BorderLayout.NORTH)

        jbTabbedPane.add("From Http", httpInputContainer)

        fileChooser = JFileChooser()
        fileChooser.addActionListener {
            revalidate()
        }
        myInputValidator.fileChooser = fileChooser
        fileChooser.fileFilter = FileNameExtensionFilter("Select a json file", "json", "txt")
        fileChooser.controlButtonsAreShown = false
        jbTabbedPane.add("From File", fileChooser)

        val centerContainer = JPanel()
        val centerBoxLayout = BoxLayout(centerContainer, BoxLayout.PAGE_AXIS)
        centerContainer.layout = centerBoxLayout
        centerContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameInputContainer)
        centerContainer.addComponentIntoVerticalBoxAlignmentLeft(jbTabbedPane)
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
        return jTextArea
    }


    private fun createMyScrollableTextComponent(myField: Component): JComponent {
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

    override fun getInputString(): String {
        if (exitCode != 0) {
            return ""
        }
        when (jbTabbedPane.selectedIndex) {
            0 -> return myField.text.trim() // Json Text
            1 -> return URL(httpUrlInput.text.trim()).readText() // Http Url
            2 -> return fileChooser.selectedFile.readText()
        }
        return ""
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        if (classNameInput.text?.isEmpty() != false) {
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

    private fun revalidate() {
        okAction.isEnabled = myInputValidator.checkInput(myField.text)
    }
}


fun createLinearLayoutVertical(): JPanel {
    val container = JPanel()
    val boxLayout = BoxLayout(container, BoxLayout.PAGE_AXIS)
    container.layout = boxLayout
    return container
}
