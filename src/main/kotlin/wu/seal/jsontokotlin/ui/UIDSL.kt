//package wu.seal.jsontokotlin.ui
//
//import com.intellij.openapi.editor.Document
//import com.intellij.openapi.editor.EditorFactory
//import com.intellij.openapi.fileTypes.LanguageFileType
//import com.intellij.openapi.fileTypes.PlainTextFileType
//import com.intellij.openapi.util.IconLoader
//import com.intellij.ui.components.*
//import com.intellij.util.ui.JBDimension
//import com.intellij.util.ui.JBUI
//import java.awt.*
//import java.awt.event.*
//import java.net.URI
//import javax.swing.*
//
///**
// * Created by Seal.Wu
// * Date: 2019-08-01
// */
//class HorizontalLinearLayoutBox : Box(BoxLayout.X_AXIS) {
//
//    operator fun <T : Component> T.invoke(): T {
//        this@HorizontalLinearLayoutBox.add(this)
//        return this
//    }
//
//    /**
//     * fill the remaining space for linear layout,like android empty space with weight value
//     */
//    fun fillSpace() {
//        add(createHorizontalGlue())
//    }
//
//    /**
//     * fill the fixed space for linear layout
//     */
//    fun fixedSpace(spaceWidth: Int) {
//        add(createHorizontalStrut(JBUI.scale(spaceWidth)))
//    }
//}
//
//class VerticalLinearLayoutBox : Box(BoxLayout.Y_AXIS) {
//
//    /**
//     * Space height between lines
//     */
//    private val lineSpaceHeight = 10
//
//    operator fun <T : Component> T.invoke(): T {
//        fixedSpace(lineSpaceHeight)
//        this@VerticalLinearLayoutBox.add(this)
//        return this
//    }
//
//    /**
//     * fill the remaining space for linear layout,like android empty space with weight value
//     */
//    fun fillSpace() {
//        add(createVerticalGlue())
//    }
//
//    /**
//     * fill the fixed space for linear layout
//     */
//    fun fixedSpace(spaceHeight: Int) {
//        add(createVerticalStrut(JBUI.scale(spaceHeight)))
//    }
//
//    /**
//     * add this component with align left style
//     */
//    fun <T : JComponent> T.putAlignLeft(): T {
//        fixedSpace(lineSpaceHeight)
//        this@VerticalLinearLayoutBox.add(horizontalLinearLayout {
//            this@putAlignLeft()
//            fillSpace()
//        })
//        return this
//    }
//}
//
//
//class SimpleBorderLayout : JPanel(BorderLayout()) {
//    var hasPutLeft = false
//    var hasPutRight = false
//    var hasPutTop = false
//    var hasPutBottom = false
//    var hasPutCenter = false
//
//    fun JComponent.putLeft() {
//        if (hasPutLeft) {
//            throw IllegalAccessError("Only Could put left one time")
//        }
//        this@SimpleBorderLayout.add(this, BorderLayout.WEST)
//        hasPutLeft = true
//    }
//
//    fun JComponent.putRight() {
//        if (hasPutRight) {
//            throw IllegalAccessError("Only Could put right one time")
//        }
//        this@SimpleBorderLayout.add(this, BorderLayout.EAST)
//        hasPutRight = true
//    }
//
//    fun JComponent.putTop() {
//        if (hasPutTop) {
//            throw IllegalAccessError("Only Could put top one time")
//        }
//        this@SimpleBorderLayout.add(this, BorderLayout.NORTH)
//        hasPutTop = true
//    }
//
//    fun JComponent.putBottom() {
//        if (hasPutBottom) {
//            throw IllegalAccessError("Only Could put bottom one time")
//        }
//        this@SimpleBorderLayout.add(this, BorderLayout.SOUTH)
//        hasPutBottom = true
//    }
//
//    fun JComponent.putCenterFill() {
//        if (hasPutCenter) {
//            throw IllegalAccessError("Only Could put center fill one time")
//        }
//        this@SimpleBorderLayout.add(this, BorderLayout.CENTER)
//        hasPutCenter = true
//    }
//}
//
//class RadioGroupScope {
//
//    private val radioButtons = mutableListOf<JRadioButton>()
//
//    var onRadioButtonSelectListener: (selectedRadioButton: JRadioButton) -> Unit = {}
//
//    fun JRadioButton.addToGroup(): JRadioButton {
//        radioButtons.add(this)
//        addActionListener {
//            onRadioButtonSelectListener(this)
//            radioButtons.forEach {
//                it.isSelected = it == this
//            }
//
//        }
//        return this
//    }
//}
//
//class MyGridLayout : JPanel() {
//    operator fun <T : Component> T.invoke(): T {
//        this@MyGridLayout.add(this)
//        return this
//    }
//}
//
///**
// * generate a horizontal linear layout which for easy adding inner views
// */
//fun horizontalLinearLayout(content: HorizontalLinearLayoutBox.() -> Unit): JPanel {
//    return JPanel().apply {
//        layout = BoxLayout(this, BoxLayout.LINE_AXIS)
//        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
//        val horizontalBox = HorizontalLinearLayoutBox()
//        horizontalBox.content()
//        add(horizontalBox)
//    }
//}
//
///**
// * generate a vertical linear layout which for easy adding inner views
// */
//fun verticalLinearLayout(content: VerticalLinearLayoutBox.() -> Unit): JPanel {
//    return JPanel().apply {
//        layout = BoxLayout(this, BoxLayout.Y_AXIS)
//        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
//        val verticalBox = VerticalLinearLayoutBox()
//        verticalBox.content()
//        add(verticalBox)
//    }
//}
//
///**
// * generate a border layout which for easy adding inner views
// */
//fun borderLayout(content: SimpleBorderLayout.() -> Unit): JPanel {
//    return SimpleBorderLayout().apply {
//        content()
//    }
//}
//
///**
// * generate a label component
// */
//fun label(text: String, textSize: Float = 13f): JLabel {
//    return JBLabel(text).apply {
//        font = font.deriveFont(textSize)
//    }
//}
//
///**
// * generate a button component
// */
//fun button(text: String, clickListener: () -> Unit = {}): JButton {
//    return JButton(text).apply {
//        addActionListener(object : AbstractAction() {
//            override fun actionPerformed(p0: ActionEvent?) {
//                clickListener()
//            }
//        })
//    }
//}
//
///**
// * generate a icon component
// */
//fun icon(iconPath: String): JLabel {
//    val icon = IconLoader.getIcon(iconPath)
//    return JBLabel(icon)
//}
//
///**
// * generate a link component
// */
//fun link(text: String, linkURL: String, linkURLColor: String = "#5597EB", maxSize: JBDimension? = null, onclick: () -> Unit = {}): JLabel {
//    return JLabel("<html><a href='$linkURL'><font color=\"$linkURLColor\">$text</font></a></html>").apply {
//        if (maxSize != null) {
//            maximumSize = maxSize
//        }
//        addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent?) {
//                Desktop.getDesktop().browse(URI(linkURL))
//                onclick()
//            }
//
//            override fun mouseEntered(e: MouseEvent?) {
//                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
//            }
//
//            override fun mouseExited(e: MouseEvent?) {
//                cursor = Cursor.getDefaultCursor()
//            }
//        })
//    }
//}
//
///**
// * generate a checkbox component
// */
//fun checkBox(text: String, isSelected: Boolean = false, onclick: (isSelectedAfterClick: Boolean) -> Unit): JBCheckBox {
//    return JBCheckBox(text, isSelected).apply {
//        addActionListener {
//            onclick(isSelected())
//        }
//    }
//}
//
///**
// * generate single lines text input component
// */
//fun textInput(initText: String, enabled: Boolean = true, onFocusLost: (textInput: JBTextField) -> Unit): JTextField {
//
//    return JBTextField().apply {
//        text = initText
//
//        addFocusListener(object : FocusListener {
//            override fun focusGained(e: FocusEvent?) {
//            }
//
//            override fun focusLost(e: FocusEvent?) {
//                onFocusLost(this@apply)
//            }
//        })
//
//        maximumSize = JBDimension(10000, 30)
//
//        isEnabled = enabled
//    }
//
//}
//
///**
// * generate multiple lines text input component
// */
//fun textAreaInput(initText: String, size: JBDimension = JBDimension(400, 50), enabled: Boolean = true
//                  , textLanguageType: LanguageFileType = PlainTextFileType.INSTANCE, onFocusLost: (textAreaInput: Document) -> Unit): JComponent {
//    val editorFactory = EditorFactory.getInstance()
//    val document = editorFactory.createDocument("").apply {
//        setReadOnly(false)
//    }
//    val editor = editorFactory.createEditor(document, null, textLanguageType, false)
//    editor.component.apply {
//        isEnabled = enabled
//        autoscrolls = true
//        preferredSize = size
//    }
//    editor.contentComponent.addFocusListener(object : FocusListener {
//        override fun focusGained(e: FocusEvent?) {
//        }
//
//        override fun focusLost(e: FocusEvent?) {
//            onFocusLost(editor.document)
//        }
//    })
//    editor.document.setText(initText)
//    return editor.component
//}
//
///**
// * generate radio button component
// */
//fun radioButton(text: String, selected: Boolean = false, onclick: (isSelectedAfterClick: Boolean) -> Unit): JRadioButton {
//    return JRadioButton(text).apply {
//        isSelected = selected
//        addActionListener {
//            onclick(true)
//        }
//    }
//}
//
///**
// * make the radio buttons in this scope will be only select one no matter how many clicks on any radio buttons
// */
//fun radioGroup(groupScope: RadioGroupScope.() -> Unit) {
//    RadioGroupScope().groupScope()
//}
//
///**
// * generate a line component
// */
//fun line(): JSeparator {
//    return JSeparator(SwingConstants.CENTER).apply {
//        maximumSize = JBDimension(10000, 10)
//        background = Color.GRAY
//    }
//}
//
///**
// * generate a scrollable component
// */
//fun scrollPanel(size: JBDimension, content: () -> Component): JBScrollPane {
//    return JBScrollPane(content()).apply {
//        preferredSize = size
//        border = null
//    }
//}
//
///**
// * generate a grid layout component
// */
//fun gridLayout(rows: Int, columns: Int, content: MyGridLayout.() -> Unit): JPanel {
//    val gridLayout = MyGridLayout()
//    gridLayout.layout = GridLayout(rows, columns, 10, 10)
//    gridLayout.content()
//    return gridLayout
//}
