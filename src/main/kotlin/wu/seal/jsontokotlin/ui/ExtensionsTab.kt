package wu.seal.jsontokotlin.ui

import com.intellij.ui.layout.panel
import com.intellij.util.ui.JBDimension
import extensions.ExtensionsCollector
import java.awt.Color
import java.awt.FlowLayout
import java.awt.LayoutManager
import javax.swing.JPanel

class ExtensionsTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {

        add(panel {
            ExtensionsCollector.extensions.forEach {
                row(separated = false) {
                    it.createUI().apply {
                        background  = Color(225,225,225)
                        preferredSize = JBDimension(500, 0)
                    }.invoke()
                }
            }
        }.apply {
            minimumSize = JBDimension(500, 300)
            preferredSize = JBDimension(500, 300)
        })
    }

}
