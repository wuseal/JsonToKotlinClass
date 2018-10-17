package wu.seal.jsontokotlin.utils

import com.google.gson.JsonArray
import java.awt.Component
import java.awt.Container
import java.util.regex.Pattern
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

fun Container.addComponentIntoVerticalBoxAlignmentLeft(component: Component, leftMargin: Int) {
    if (layout is BoxLayout) {

        val hBox = Box.createHorizontalBox()
        hBox.add(Box.createHorizontalStrut(leftMargin))
        hBox.add(component)
        hBox.add(Box.createHorizontalGlue())
        add(hBox)
    }

}


/**
 * How many substring in the parent string
 */
fun String.numberOf(subString: String): Int {
    var count = 0
    val pattern = Pattern.compile(subString)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        count++
    }
    return count
}

/**
 * array only has one element
 */
private fun JsonArray.onlyHasOneElement(): Boolean {
    return size() == 1
}

/**
 * array only has one object element
 */
private fun JsonArray.onlyHasOneObjectElement(): Boolean {
    return size() == 1 && get(0).isJsonObject
}

/**
 * array only has object element
 */
private fun JsonArray.allObjectElement(): Boolean {
    forEach {
        if (it.isJsonObject.not()) {
            return false
        }
    }
    return  true
}

/**
 * if Multidimensional Arrays only has one element
 */
fun JsonArray.onlyHasOneElementRecursive(): Boolean {

    if (size() == 0) {
        return false
    }
    if (onlyHasOneElement().not()) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonObject || get(0).isJsonNull) {
        return true
    }

    return get(0).asJsonArray.onlyHasOneElementRecursive()
}


/**
 * if Multidimensional Arrays only has one element
 */
fun JsonArray.onlyHasOneObjectElementRecursive(): Boolean {

    if (size() == 0) {
        return false
    }
    if (onlyHasOneElement().not()) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonNull) {
        return false
    }

    if (get(0).isJsonObject) {
        return true
    }
    return get(0).asJsonArray.onlyHasOneObjectElementRecursive()
}


/**
 * if Multidimensional Arrays only has one dimension contains element and the elements are all object element
 */
fun JsonArray.onlyOneSubArrayContainsElementAndAllObjectRecursive(): Boolean {

    if (size() == 0) {
        return false
    }

    if (get(0).isJsonPrimitive || get(0).isJsonNull) {
        return false
    }

    if (allObjectElement()) {
        return true
    }

    return get(0).asJsonArray.onlyOneSubArrayContainsElementAndAllObjectRecursive()
}