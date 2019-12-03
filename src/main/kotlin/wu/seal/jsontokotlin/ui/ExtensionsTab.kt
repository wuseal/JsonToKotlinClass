package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import extensions.ExtensionsCollector
import java.awt.BorderLayout
import javax.swing.JPanel

class ExtensionsTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {
    init {
        val content = scrollPanel(JBDimension(500, 300)) {
            verticalLinearLayout {
                ExtensionsCollector.extensions.forEach {
                    it.createUI()()
                }
            }
        }
        add(content, BorderLayout.CENTER)
    }
}
