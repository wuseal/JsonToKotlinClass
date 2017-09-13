package wu.seal.jsontokotlin

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBDimension
import java.awt.BorderLayout
import java.awt.ComponentOrientation
import javax.swing.*

/**
 *
 * Created by LENOVO on 2017/9/13.
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

        val propertyPane = JPanel(BorderLayout(5, 5))
        propertyPane.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        val radioButtonVal = JRadioButton("Val")

        radioButtonVal.addActionListener {
            ConfigManager.isPropertiesVar = false
        }
        val radioButtonVar = JRadioButton("Var")
        radioButtonVar.addActionListener {
            ConfigManager.isPropertiesVar = true
        }

        if (ConfigManager.isPropertiesVar) {

            radioButtonVar.isSelected = true
        } else {
            radioButtonVal.isSelected = true
        }

        val buttonGroupProperty = ButtonGroup()
        buttonGroupProperty.add(radioButtonVal)
        buttonGroupProperty.add(radioButtonVar)

        propertyPane.add(radioButtonVal, BorderLayout.NORTH)
        propertyPane.add(radioButtonVar, BorderLayout.CENTER)
        tabbedPane.add("Property Keyword", propertyPane)

        tabbedPane.minimumSize = JBDimension(300, 180)

        return tabbedPane
    }


    override fun dismiss() {
        close(CANCEL_EXIT_CODE)
    }


    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

}
