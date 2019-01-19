package extensions.wu.seal

import com.intellij.ui.layout.panel
import extensions.Extension
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import javax.swing.JCheckBox
import javax.swing.JPanel

object AllUpperCase :Extension(){

    private const val configKey = "wu.seal.all_to_be_upper_case"


    override fun createUI(): JPanel {

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

        return if (getConfig(configKey).toBoolean()) {
            //make all properties name to be all upper case
            val newProperties = kotlinDataClass.properties.map { it.copy(name = it.name.toUpperCase()) }

            kotlinDataClass.copy(properties = newProperties)

        } else {

            kotlinDataClass
        }
    }
}