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

interface IConfigSettingDialog {

    fun show()

    fun dismiss()

}

class ConfigSettingDialog(canBeParent: Boolean) : DialogWrapper(canBeParent), IConfigSettingDialog {

    init {
        init()
        title = "Config Settings"
    }


    override fun createCenterPanel(): JComponent? {

        val tabbedPane = JTabbedPane()

        val propertyPanelTab = createPropertyPane()

        val otherConfigTab = createOtherSettingTab()

        val JSONconverterTab = createTargetJsonLibConfigPanel()

        tabbedPane.add("Property", propertyPanelTab)

        tabbedPane.add("JSON Converter", JSONconverterTab)

        tabbedPane.add("Other", otherConfigTab)

        tabbedPane.minimumSize = JBDimension(500, 300)

        return tabbedPane
    }

    private fun createOtherSettingTab() = ConfigSettingsOthersTab(true)

    private fun createTargetJsonLibConfigPanel() = TargetJsonLibConfigPanelContainer(true)

    private fun createCommentConfigPanel() = CommentConfigPanel(true)

    private fun createPropertyPane(): JPanel {


        return PropertyPanel(true)
    }


    override fun dismiss() {
        close(CANCEL_EXIT_CODE)
    }


    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
