package wu.seal.jsontokotlin.utils

import java.awt.Component
import java.awt.Container
import javax.swing.Box
import javax.swing.BoxLayout

/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */

fun Container.addComponentIntoVerticalBoxAlignmentLeft(component: Component) {
    if (layout is BoxLayout) {

        val hBox = Box.createHorizontalBox()
        hBox.add(component)
        hBox.add(Box.createHorizontalGlue())
        add(hBox)
    }

}

fun Container.addComponentIntoVerticalBoxAlignmentLeft(component: Component, leftMargin:Int) {
    if (layout is BoxLayout) {

        val hBox = Box.createHorizontalBox()
        hBox.add(Box.createHorizontalStrut(leftMargin))
        hBox.add(component)
        hBox.add(Box.createHorizontalGlue())
        add(hBox)
    }

}