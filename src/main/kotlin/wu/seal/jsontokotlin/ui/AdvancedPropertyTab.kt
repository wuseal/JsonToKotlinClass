package wu.seal.jsontokotlin.ui

import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import java.awt.BorderLayout
import java.awt.LayoutManager
import javax.swing.JPanel

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
class AdvancedPropertyTab(layout: LayoutManager?, isDoubleBuffered: Boolean) : JPanel(layout, isDoubleBuffered) {

    constructor(isDoubleBuffered: Boolean) : this(BorderLayout(), isDoubleBuffered)

    init {
        val content = verticalLinearLayout {
            label("Keyword")()
            radioGroup {
                radioButton("Val", !ConfigManager.isPropertiesVar) {
                    ConfigManager.isPropertiesVar = false
                }().addToGroup()

                radioButton("Var", ConfigManager.isPropertiesVar) {
                    ConfigManager.isPropertiesVar = true
                }().addToGroup()
            }
            line()()
            label("Type")()
            radioGroup {
                radioButton("Non-Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.NotNullable) {
                    ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.NotNullable
                }().addToGroup()

                radioButton("Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable) {
                    ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.Nullable
                }().addToGroup()

                radioButton("Auto Determine Nullable Or Not From JSON Value", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot) {
                    ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.AutoDeterMineNullableOrNot
                }().addToGroup()
            }
            line()()
            label("Default Value Strategy")()
            radioGroup {
                radioButton("Don't Init With Default Value", ConfigManager.defaultValueStrategy == DefaultValueStrategy.None) {
                    ConfigManager.defaultValueStrategy = DefaultValueStrategy.None
                }().addToGroup()
                radioButton("Init With Non-Null Default Value (Avoid Null)", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull) {
                    ConfigManager.defaultValueStrategy = DefaultValueStrategy.AvoidNull
                }().addToGroup()
                radioButton("Init With Default Value Null When Property Is Nullable", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AllowNull) {
                    ConfigManager.defaultValueStrategy = DefaultValueStrategy.AllowNull
                }().addToGroup()
            }

        }
        val scrollContent = scrollPanel(JBDimension(500, 300)) {
            content
        }
        add(scrollContent, BorderLayout.CENTER)
    }
}
