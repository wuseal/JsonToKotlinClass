package wu.seal.jsontokotlin

import com.intellij.ide.util.PropertiesComponent
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Config Manager
 * Created by Seal.Wu on 2018/2/7.
 */
object ConfigManager : IConfigManager {

    private const val INDENT_KEY = "json-to-kotlin-class-indent-space-number"
    private const val ENABLE_MAP_TYP_KEY = "json-to-kotlin-class-enable-map-type"

    var indent: Int
        get() = if (TestConfig.isTestModel) TestConfig.indent else PropertiesComponent.getInstance().getInt(
            INDENT_KEY,
            4
        )
        set(value) = if (TestConfig.isTestModel) {
            TestConfig.indent = value
        } else PropertiesComponent.getInstance().setValue(INDENT_KEY, value, 4)

    var enableMapType: Boolean
        get() = if (TestConfig.isTestModel) TestConfig.enableMapType else PropertiesComponent.getInstance().getBoolean(
            ENABLE_MAP_TYP_KEY,
            false
        )
        set(value) = if (TestConfig.isTestModel) {
            TestConfig.enableMapType = value
        } else PropertiesComponent.getInstance().setValue(ENABLE_MAP_TYP_KEY, value, false)


}