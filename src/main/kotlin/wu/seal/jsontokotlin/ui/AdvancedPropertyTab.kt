package wu.seal.jsontokotlin.ui

import com.intellij.util.ui.JBDimension
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
class AdvancedPropertyTab(isDoubleBuffered: Boolean) : JPanel(BorderLayout(), isDoubleBuffered) {

    init {
        jScrollPanel(JBDimension(500, 300)) {
            jVerticalLinearLayout {
                jLabel("Keyword")
                jButtonGroup {
                    jRadioButton("Val", !ConfigManager.isPropertiesVar, { ConfigManager.isPropertiesVar = false })
                    jRadioButton("Var", ConfigManager.isPropertiesVar, { ConfigManager.isPropertiesVar = true })
                }
                jLine()
                jLabel("Type")
                jButtonGroup {
                    jRadioButton("Non-Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.NotNullable,
                            { ConfigManager.propertyTypeStrategy = PropertyTypeStrategy.NotNullable })
                    jRadioButton("Nullable", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable,
                            { ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.Nullable })
                    jRadioButton("Auto Determine Nullable Or Not From JSON Value", ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot,
                            { ConfigManager.propertyTypeStrategy == PropertyTypeStrategy.AutoDeterMineNullableOrNot })
                }
                jLine()
                jLabel("Default Value Strategy")
                jButtonGroup {
                    jRadioButton("Don't Init With Default Value", ConfigManager.defaultValueStrategy == DefaultValueStrategy.None,
                            { ConfigManager.defaultValueStrategy == DefaultValueStrategy.None })
                    jRadioButton("Init With Non-Null Default Value (Avoid Null)", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull,
                            { ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull })
                    jRadioButton("Init With Default Value Null When Property Is Nullable", ConfigManager.defaultValueStrategy == DefaultValueStrategy.AllowNull,
                            { ConfigManager.defaultValueStrategy == DefaultValueStrategy.AllowNull })
                }
            }
        }
    }
}
