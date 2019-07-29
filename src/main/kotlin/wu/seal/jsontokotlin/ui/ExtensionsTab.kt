package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBEmptyBorder
import extensions.ExtensionsCollector
import java.awt.BorderLayout
import java.awt.LayoutManager
import javax.swing.JPanel

class ExtensionsTab(layoutManager: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layoutManager, isDoubleBuffered) {

    constructor(isDoubleBuffered: Boolean) : this(BorderLayout(), isDoubleBuffered)

    init {
        add(verticalLinearLayout {
            ExtensionsCollector.extensions.forEach {
                it.createUI().apply {
                    border = JBEmptyBorder(5, 3, 5, 3)
                }()
            }
        },BorderLayout.CENTER)
    }

}
