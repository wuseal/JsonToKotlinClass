package wu.seal.jsontokotlin.ui

import extensions.ExtensionsCollector
import java.awt.BorderLayout
import java.awt.LayoutManager
import javax.swing.JPanel

class ExtensionsTab(layoutManager: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layoutManager, isDoubleBuffered) {

    constructor(isDoubleBuffered: Boolean) : this(BorderLayout(), isDoubleBuffered)

    init {
        add(verticalLinearLayout {
            ExtensionsCollector.extensions.forEach {
                it.createUI()()
            }
        },BorderLayout.CENTER)
    }

}
