package wu.seal.jsontokotlin.ui

import com.google.gson.*
import com.intellij.json.JsonFileType
import com.intellij.openapi.application.ApplicationManager
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
import java.awt.*
import java.awt.datatransfer.DataFlavor
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URI
import java.net.URL
import javax.swing.*
import javax.swing.text.AttributeSet
import javax.swing.text.JTextComponent
import javax.swing.text.PlainDocument

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
class JsonInputDialog(classsName: String, private val project: Project) : Messages.InputDialog(
    project,
    "Please input the JSON String and class name to generate Kotlin data class",
    "Generate Kotlin Data Class Code",
    null,
    "",
    myInputValidator
) {
    private lateinit var jsonContentEditor: Editor

    private val prettyGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create()

    init {
        setOKButtonText("Generate")
        myField.text = classsName
    }

    override fun createNorthPanel(): JComponent? {
        val jsonTitle = JBLabel("JSON Text: ").apply {
            font = font.deriveFont(14f)
        }
        val jsonTip =
            JBLabel("Tips: you can use JSON string、http urls or local file just right click on text area").apply {
                font = font.deriveFont(12f)
            }

        val formatButton = JButton("JSON Format")
            .apply {
                addActionListener(object : AbstractAction() {
                    override fun actionPerformed(p0: ActionEvent?) {
                        handleFormatJSONString()
                    }
                })
            }
        val jsonInputTitleContainer = JPanel()
            .apply {
                border = JBEmptyBorder(0, 0, 5, 0)
                layout = BoxLayout(this, BoxLayout.LINE_AXIS)
                add(jsonTitle)
                add(jsonTip)
                add(Box.createHorizontalGlue())
                add(formatButton)
            }
        val rightContainer = JPanel(BorderLayout()).apply {

            add(JLabel(myMessage).apply {
                font = font.deriveFont(12f)
            }, BorderLayout.NORTH)
            add(jsonInputTitleContainer, BorderLayout.CENTER)

        }

        val icon = IconLoader.getIcon("/icons/icon_json_input_dialog.png")

        val iconLabel = JBLabel(icon).apply {
            minimumSize = JBDimension(42, 42)
            verticalAlignment = SwingConstants.TOP
            border = JBEmptyBorder(0, 0, 0, 5)
        }
        return JPanel(BorderLayout())
            .apply {
                border = JBEmptyBorder(0, 0, 5, 0)
                add(iconLabel, BorderLayout.WEST)
                add(rightContainer, BorderLayout.CENTER)
            }

    }

    override fun createCenterPanel(): JComponent? {
        jsonContentEditor = createJsonContentEditor()
        myInputValidator.jsonInputEditor = jsonContentEditor


        val jsonInputContainer = JPanel(BorderLayout())
            .apply {
                preferredSize = JBDimension(700, 400)
                border = JBEmptyBorder(5, 0, 5, 5)
                add(jsonContentEditor.component, BorderLayout.CENTER)
            }

        myField = createTextFieldComponent()

        val classNameInputContainer = createLinearLayoutVertical()
            .apply {
                val classNameTitle = JBLabel("Class Name:")
                classNameTitle.border = JBEmptyBorder(5, 0, 5, 0)
                addComponentIntoVerticalBoxAlignmentLeft(classNameTitle)
                addComponentIntoVerticalBoxAlignmentLeft(myField)
                preferredSize = JBDimension(500, 56)
                font = font.deriveFont(14f)
            }

        val centerInputContainer = JPanel(BorderLayout())
            .apply {
                add(jsonInputContainer, BorderLayout.CENTER)
                add(classNameInputContainer, BorderLayout.SOUTH)
            }

        val settingContainer = createAdvancedPanel()

        return JPanel(BorderLayout())
            .apply {
                add(centerInputContainer, BorderLayout.CENTER)
                add(settingContainer, BorderLayout.SOUTH)
            }
    }

    private fun createAdvancedPanel(): JPanel {
        val advancedButton = JButton("Advanced")
            .apply {
                horizontalAlignment = SwingConstants.CENTER
                addActionListener(object : AbstractAction() {
                    override fun actionPerformed(e: ActionEvent) {
                        AdvancedDialog(false).show()
                    }
                })
            }

        val tip = JLabel("Like this version? Please star here: ")
        val projectLink =
            JLabel("<html><a href='https://github.com/wuseal/JsonToKotlinClass'>https://github.com/wuseal/JsonToKotlinClass</a></html>")

        projectLink.maximumSize =
            JBDimension(210, 30)//if not add this line code，the `add(Box.createHorizontalGlue())`code will not do work

        projectLink.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                Desktop.getDesktop().browse(URI("https://github.com/wuseal/JsonToKotlinClass"))
            }

            override fun mouseEntered(e: MouseEvent?) {
                projectLink.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            }

            override fun mouseExited(e: MouseEvent?) {
                projectLink.cursor = Cursor.getDefaultCursor()
            }
        })
        return JPanel()
            .apply {
                border = JBEmptyBorder(0, 0, 10, 7)
                layout = BoxLayout(this, BoxLayout.LINE_AXIS)
                add(advancedButton)
                add(Box.createHorizontalGlue())
                add(tip)
                add(projectLink)
            }
    }

    private fun createJsonContentEditor(): Editor {
        val editorFactory = EditorFactory.getInstance()
        val document = editorFactory.createDocument("").apply {
            setReadOnly(false)
            addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
                override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent?) = revalidate()

                override fun beforeDocumentChange(event: com.intellij.openapi.editor.event.DocumentEvent?) = Unit
            })
        }

        val editor = editorFactory.createEditor(document, null, JsonFileType.INSTANCE, false)

        editor.component
            .apply {
                isEnabled = true
                preferredSize = Dimension(640, 480)
                autoscrolls = true
            }


        val contentComponent = editor.contentComponent
        contentComponent.isFocusable = true
        contentComponent.componentPopupMenu = JPopupMenu().apply {
            add(createPasteFromClipboardMenuItem())
            add(createRetrieveContentFromHttpURLMenuItem())
            add(createLoadFromLocalFileMenu())
        }

        return editor
    }

    override fun createTextFieldComponent(): JTextComponent {

        return JTextField()
            .apply {
                preferredSize = JBDimension(400, 40)
                document = object : PlainDocument() {
                    override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
                        str ?: return
                        super.insertString(
                            offs,
                            str.filter { it.isLetterOrDigit() || it in listOf('_', '$') }.take(252),
                            a
                        )
                    }
                }
            }
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
                ApplicationManager.getApplication().runWriteAction {
                    jsonContentEditor.document.setText(content.replace("\r\n", "\n"))
                }
            }
        }
    }

    /**
     * get the user input class name
     */
    fun getClassName(): String {
        return if (exitCode == 0) {
            val name = myField.text.trim()
            name.let { if (it.first().isDigit() || it.contains('$')) "`$it`" else it }
        } else ""
    }

    override fun getInputString(): String = if (exitCode == 0) jsonContentEditor.document.text.trim() else ""

    override fun getPreferredFocusedComponent(): JComponent? {
        return jsonContentEditor.contentComponent
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

    return JPanel()
        .apply {
            layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        }
}
