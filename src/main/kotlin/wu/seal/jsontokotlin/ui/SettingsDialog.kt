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

class SettingsDialog(canBeParent: Boolean) : DialogWrapper(canBeParent) {

    init {
        init()
        title = "Settings"
    }


    override fun createCenterPanel(): JComponent? {

        val tabbedPane = JBTabbedPane()

        val propertyPanelTab = createPropertyTab()

        val otherConfigTab = createOtherSettingTab()

        val annotationTab = createAnnotationTab()

        tabbedPane.add("Property", propertyPanelTab)

        tabbedPane.add("Annotation", annotationTab)

        tabbedPane.add("Other", otherConfigTab)

        tabbedPane.minimumSize = JBDimension(500, 300)

        return tabbedPane
    }

    private fun createOtherSettingTab() = SettingsOtherTab(true)

    private fun createAnnotationTab() = SettingsAnnotationTab(true)


    private fun createPropertyTab(): JPanel {


        return SettingsPropertyTab(true)
    }


    fun dismiss() {
        close(CANCEL_EXIT_CODE)
    }


    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
