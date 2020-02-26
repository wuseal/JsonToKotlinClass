package wu.seal.jsontokotlin.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBTabbedPane
import com.intellij.util.ui.JBDimension
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JPanel

/**
 *
 * Created by Seal.Wu on 2017/9/13.
 */

class AdvancedDialog(canBeParent: Boolean) : DialogWrapper(canBeParent) {

    init {
        init()
        title = "Advanced"
    }


    override fun createCenterPanel(): JComponent? {

        return JBTabbedPane().apply {
            add("Property", createPropertyTab())
            add("Annotation", createAnnotationTab())
            add("Other", createOtherSettingTab())
            add("Extensions", createExtensionTab())
            minimumSize = JBDimension(500, 300)
        }
    }

    private fun createOtherSettingTab() = AdvancedOtherTab(true)

    private fun createAnnotationTab() = AdvancedAnnotationTab(true)


    private fun createExtensionTab() = ExtensionsTab(true)
    private fun createPropertyTab(): JPanel {
        return AdvancedPropertyTab(true)
    }


    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
