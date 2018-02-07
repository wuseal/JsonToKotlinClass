package wu.seal.jsontokotlin.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBDimension
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 *
 * Created by Seal.Wu on 2017/9/13.
 */

interface ISettingsDialog {

    fun show()

    fun dismiss()

}

class SettingsDialog(canBeParent: Boolean) : DialogWrapper(canBeParent), ISettingsDialog {

    init {
        init()
        title = "Settings"
    }


    override fun createCenterPanel(): JComponent? {

        val tabbedPane = JTabbedPane()

        val propertyPanelTab = createPropertyTab()

        val otherConfigTab = createOtherSettingTab()

        val jSONConverterTab = createJSONConverterTab()

        tabbedPane.add("Property", propertyPanelTab)

        tabbedPane.add("JSON Converter", jSONConverterTab)

        tabbedPane.add("Other", otherConfigTab)

        tabbedPane.minimumSize = JBDimension(500, 300)

        return tabbedPane
    }

    private fun createOtherSettingTab() = SettingsOtherTab(true)

    private fun createJSONConverterTab() = SettingsJSONConverterTab(true)


    private fun createPropertyTab(): JPanel {


        return SettingsPropertyTab(true)
    }


    override fun dismiss() {
        close(CANCEL_EXIT_CODE)
    }


    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
