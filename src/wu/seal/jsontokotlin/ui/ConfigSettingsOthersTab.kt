package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.JBUI
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.addComponentIntoVerticalBoxAlignmentLeft
import java.awt.FlowLayout
import java.awt.LayoutManager
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * others settings tab in config settings dialog
 * Created by Seal.Wu on 2018/2/6.
 */
class ConfigSettingsOthersTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(layout: LayoutManager?) : this(layout, false)

    constructor(isDoubleBuffered: Boolean) : this(FlowLayout(), isDoubleBuffered)

    init {
        val boxLayout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        setLayout(boxLayout)
        val bordWidth = JBUI.scale(10)
        border = EmptyBorder(bordWidth, bordWidth, bordWidth, bordWidth)

        val enableComment = JBCheckBox("enable comment")
        enableComment.isSelected = ConfigManager.isCommentOff.not()
        enableComment.addActionListener { ConfigManager.isCommentOff = enableComment.isSelected.not() }



        val enableInnerClassModel = JBCheckBox("enable inner class model")
        enableInnerClassModel.isSelected = ConfigManager.isInnerClassModel
        enableInnerClassModel.addActionListener { ConfigManager.isInnerClassModel = enableInnerClassModel.isSelected }

        add(Box.createVerticalStrut(JBUI.scale(10)))

        addComponentIntoVerticalBoxAlignmentLeft(enableComment)

        add(Box.createVerticalStrut(JBUI.scale(20)))

        addComponentIntoVerticalBoxAlignmentLeft(enableInnerClassModel)
    }

}