package wu.seal.jsontokotlin.ui

import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel


class HorizontalLinearLayoutBox : Box(BoxLayout.X_AXIS) {

    operator fun JComponent.invoke() {
        this@HorizontalLinearLayoutBox.add(this)
    }
}

class VerticalLinearLayoutBox : Box(BoxLayout.Y_AXIS) {

    operator fun JComponent.invoke() {
        this@VerticalLinearLayoutBox.add(this)
    }
}
fun horizontalLinearLayout(content: HorizontalLinearLayoutBox.() -> Unit): JPanel {
    return JPanel().apply {
        layout = BoxLayout(this,BoxLayout.X_AXIS )
        val horizontalBox = HorizontalLinearLayoutBox()
        horizontalBox.content()
        horizontalBox.add(Box.createHorizontalGlue())
        add(horizontalBox)
    }
}
fun verticalLinearLayout(content: VerticalLinearLayoutBox.() -> Unit): JPanel {
    return JPanel().apply {
        layout = BoxLayout(this,BoxLayout.Y_AXIS )
        val verticalBox = VerticalLinearLayoutBox()
        verticalBox.content()
        verticalBox.add(Box.createVerticalGlue())
        add(verticalBox)
    }
}
