package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import extensions.ExtensionsCollector
import java.awt.BorderLayout
import javax.swing.JPanel

class ExtensionsTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {
    init {
        jScrollPanel(JBDimension(500, 300)) {
            jVerticalLinearLayout {
                ExtensionsCollector.extensions.forEach {
                    add(it.createUI())
                }
                fixedSpace(30)
            }
        }
    }
}
