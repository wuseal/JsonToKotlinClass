package extensions.wu.seal

import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import javax.swing.JCheckBox
import javax.swing.JPanel

object AllUpperCase :Extension(){

    override fun createUI(): JPanel {

        val configKey = "wu.seal.all_to_be_upper_case"

        val checkBox = JCheckBox("Make all properties name to be all upper case").apply {
            isSelected = getConfig(configKey).toBoolean()
            addActionListener {
                setConfig(configKey, isSelected.toString())
            }
        }

        return panel {
            row {
                checkBox()
            }
        }

    }

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        //make all properties name to be all upper case
        val newProperties = kotlinDataClass.properties.map { it.copy(name = it.name.toUpperCase()) }

        return kotlinDataClass.copy(properties = newProperties)
    }
}