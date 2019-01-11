package wu.seal.jsontokotlin.ui

import com.google.gson.*
import com.intellij.json.JsonFileType
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.progress.util.DispatchThreadProgressWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import wu.seal.jsontokotlin.feedback.FormatJSONAction
import wu.seal.jsontokotlin.feedback.sendActionInfo
import wu.seal.jsontokotlin.utils.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.net.URL
import javax.swing.*
import javax.swing.text.JTextComponent

/**
 * Dialog widget relative
 * Created by Seal.wu on 2017/9/21.
 */


class MyInputValidator : InputValidator {
    lateinit var jsonInputEditor: Editor

    override fun checkInput(className: String): Boolean {
        return className.isNotBlank() && inputIsValidJson(jsonInputEditor.document.text)
    }

    override fun canClose(inputString: String): Boolean = true

    private fun inputIsValidJson(string: String) = try {
        val jsonElement = JsonParser().parse(string)
        (jsonElement.isJsonObject || jsonElement.isJsonArray)
    } catch (e: JsonSyntaxException) {
        false
    }
}

val myInputValidator = MyInputValidator()

/**
 * Json input Dialog
 */
class JsonInputDialog(classsName: String, private val project: Project) : Messages.InputDialog(project, "Please input the class name and JSON String to generate Kotlin data class", "Generate Kotlin Data Class Code", IconLoader.getIcon("/icons/logo_96x96.png"), "", myInputValidator) {
    private lateinit var jsonContentEditor: Editor

    private val prettyGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    init {
        setOKButtonText("Generate")
        myField.text = classsName
    }

    override fun createMessagePanel(): JPanel {
        val messagePanel = JPanel(BorderLayout())
        if (myMessage != null) {
            messagePanel.add(createTextComponent(), BorderLayout.NORTH)
        }

        jsonContentEditor = createJsonContentEditor()

        val classNameInputContainer = createLinearLayoutVertical()
        val classNameTitle = JBLabel("Class Name: ")
        classNameTitle.border = JBEmptyBorder(5, 0, 5, 0)
        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(classNameTitle)
        myField = createTextFieldComponent()
        myInputValidator.jsonInputEditor = jsonContentEditor

        classNameInputContainer.addComponentIntoVerticalBoxAlignmentLeft(this.myField)
        classNameInputContainer.preferredSize = JBDimension(500, 56)

        val jsonInputContainer = createLinearLayoutVertical()
        jsonInputContainer.preferredSize = JBDimension(700, 400)
        jsonInputContainer.border = JBEmptyBorder(5, 0, 5, 5)
        val jsonTitle = JBLabel("JSON Text:")
        jsonTitle.border = JBEmptyBorder(5, 0, 5, 0)
        jsonInputContainer.addComponentIntoVerticalBoxAlignmentLeft(jsonTitle)
        jsonInputContainer.addComponentIntoVerticalBoxAlignmentLeft(jsonContentEditor.component)


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

    private fun createJsonContentEditor(): Editor {
        val editorFactory = EditorFactory.getInstance()
        val document = editorFactory.createDocument("").apply {  }
        document.setReadOnly(false)
        document.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
            override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent?) = revalidate()

            override fun beforeDocumentChange(event: com.intellij.openapi.editor.event.DocumentEvent?) = Unit
        })

        val editor = editorFactory.createEditor(document, null, JsonFileType.INSTANCE, false)
        val component = editor.component
        component.isEnabled = true
        component.preferredSize = Dimension(640, 480)
        component.autoscrolls = true

        editor.contentComponent.componentPopupMenu = JPopupMenu().apply {
            add(createPasteFromClipboardMenuItem())
            add(createRetrieveContentFromHttpURLMenuItem())
            add(createLoadFromLocalFileMenu())
        }

        return editor
    }

    override fun createTextFieldComponent(): JTextComponent {
        val classNameInput = JTextField()
        classNameInput.preferredSize = JBDimension(400, 40)
        classNameInput.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent) {
                val keyChar = e.keyChar
                if (keyChar == '˚') {
                    e.consume()
                }
            }
        })
        return classNameInput
    }

    private fun createPasteFromClipboardMenuItem() = JMenuItem("Paste from clipboard").apply {
        addActionListener {
            val transferable = Toolkit.getDefaultToolkit().systemClipboard.getContents(null)
            if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                jsonContentEditor.document.setText(transferable.getTransferData(DataFlavor.stringFlavor).toString())
            }
        }
    }

    private fun createRetrieveContentFromHttpURLMenuItem() = JMenuItem("Retrieve content from Http URL").apply {
        addActionListener {
            val url = Messages.showInputDialog("URL", "Retrieve content from Http URL", null, null, UrlInputValidator)
            val p = DispatchThreadProgressWindow(false, project)
            p.isIndeterminate = true
            p.setRunnable {
                try {
                    val urlContent = URL(url).readText()
                    jsonContentEditor.document.setText(urlContent.replace("\r\n", "\n"))
                } finally {
                    p.stop()
                }
            }
            p.start()
        }
    }

    private fun createLoadFromLocalFileMenu() = JMenuItem("Load from local file").apply {
        addActionListener {
            FileChooser.chooseFile(FileChooserDescriptor(true, false, false, false, false, false), null, null) { file ->
                val content = String(file.contentsToByteArray())
                jsonContentEditor.document.setText(content)
            }
        }
    }

    /**
     * get the user input class name
     */
    fun getClassName(): String = if (exitCode == 0) this.myField.text.trim() else ""

    override fun getInputString(): String = if (exitCode == 0) jsonContentEditor.document.text.trim() else ""

    override fun getPreferredFocusedComponent(): JComponent? {
        return if (this.myField.text?.isEmpty() != false) {
            this.myField
        } else {
            jsonContentEditor.contentComponent
        }
    }

    fun handleFormatJSONString() {
        val currentText = jsonContentEditor.document.text
        if (currentText.isNotEmpty()) {
            try {
                val jsonElement = prettyGson.fromJson<JsonElement>(currentText, JsonElement::class.java)
                val formatJSON = prettyGson.toJson(jsonElement)
                jsonContentEditor.document.setText(formatJSON)
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
