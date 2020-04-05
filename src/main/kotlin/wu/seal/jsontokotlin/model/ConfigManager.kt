package wu.seal.jsontokotlin.model

import com.intellij.ide.util.PropertiesComponent
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Config Manager
 * Created by Seal.Wu on 2018/2/7.
 */
object ConfigManager : IConfigManager {

    private const val INDENT_KEY = "json-to-kotlin-class-indent-space-number"
    private const val ENABLE_MAP_TYP_KEY = "json-to-kotlin-class-enable-map-type"
    private const val ENABLE_MINIMAL_ANNOTATION = "json-to-kotlin-class-enable-minimal-annotation"
    private const val PARENT_CLASS_TEMPLATE = "json-to-kotlin-class-parent-class-template"
    private const val KEYWORD_PROPERTY_EXTENSIONS_CONFIG = "json-to-kotlin-class-keyword-extensions-config"
    private const val AUTO_DETECT_JSON_SCHEMA = "json-to-kotlin-class-auto-detect-json-schema"

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

    var enableMinimalAnnotation: Boolean
        get() = if (TestConfig.isTestModel) {
            TestConfig.enableMinimalAnnotation
        } else {
            PropertiesComponent.getInstance().getBoolean(ENABLE_MINIMAL_ANNOTATION, false)
        }
        set(value) {
            if (TestConfig.isTestModel) {
                TestConfig.enableMinimalAnnotation = value
            } else {
                PropertiesComponent.getInstance().setValue(ENABLE_MINIMAL_ANNOTATION, value, false)
            }
        }

    var autoDetectJsonScheme: Boolean
        get() = if (TestConfig.isTestModel) {
            TestConfig.autoDetectJsonScheme
        } else {
            PropertiesComponent.getInstance().getBoolean(AUTO_DETECT_JSON_SCHEMA, true)
        }
        set(value) {
            if (TestConfig.isTestModel) {
                TestConfig.autoDetectJsonScheme = value
            } else {
                PropertiesComponent.getInstance().setValue(AUTO_DETECT_JSON_SCHEMA, value, true)
            }
        }

    var parenClassTemplate: String
        get() = if (TestConfig.isTestModel) {
            TestConfig.parenClassTemplate
        } else {
            PropertiesComponent.getInstance().getValue(PARENT_CLASS_TEMPLATE, "")
        }
        set(value) {
            if (TestConfig.isTestModel) {
                TestConfig.parenClassTemplate = value
            } else {
                PropertiesComponent.getInstance().setValue(PARENT_CLASS_TEMPLATE, value, "")
            }
        }

    var extensionsConfig: String
        get() = if (TestConfig.isTestModel) {
            TestConfig.extensionsConfig
        } else {
            PropertiesComponent.getInstance().getValue(KEYWORD_PROPERTY_EXTENSIONS_CONFIG, "")
        }
        set (value) {
            if (TestConfig.isTestModel) {
                TestConfig.extensionsConfig = value
            } else {
                PropertiesComponent.getInstance().setValue(KEYWORD_PROPERTY_EXTENSIONS_CONFIG, value, "")
            }
        }

}