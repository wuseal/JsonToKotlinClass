package wu.seal.jsontokotlin.test

import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.supporter.GsonSupporter

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
/** 
 * config for test unit
 */
object TestConfig {
    /**
     * If it is in test model
     */
    var isTestModel = false
    var isCommentOff = false
    var isPropertiesVar = false
    var targetJsonConvertLib = TargetJsonConverter.Gson
    var propertyTypeStrategy = PropertyTypeStrategy.NotNullable
    var initWithDefaultValue = true
    var isNestedClassModel = true

    var customPropertyAnnotationFormatString = "@Optional\n@SerialName(\"%s\")"
    var customAnnotaionImportClassString = "import kotlinx.serialization.SerialName\n" +
            "import kotlinx.serialization.Serializable" + "\n" + "import kotlinx.serialization.Optional"

    var customClassAnnotationFormatString = "@Serializable"

    var indent: Int = 4

    var enableMapType: Boolean = true
    var enableAutoReformat: Boolean = true

    var enableMinimalAnnotation = false

    private var state = State()

    fun setToTestInitState() {
        isTestModel = true
        isCommentOff = false
        isPropertiesVar = false
        targetJsonConvertLib = TargetJsonConverter.Gson
        propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        initWithDefaultValue = true
        isNestedClassModel = true
        customPropertyAnnotationFormatString = "@Optional\n@SerialName(\"%s\")"
        customAnnotaionImportClassString = "import kotlinx.serialization.SerialName\n" +
                "import kotlinx.serialization.Serializable" + "\n" + "import kotlinx.serialization.Optional"

        customClassAnnotationFormatString = "@Serializable"

        enableMinimalAnnotation = false

    }


    fun setToTestPureNestedClassInitState() {
        isTestModel = true
        isCommentOff = true
        isPropertiesVar = false
        targetJsonConvertLib = TargetJsonConverter.None
        propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        initWithDefaultValue = false
        isNestedClassModel = true
        customPropertyAnnotationFormatString = ""
        customClassAnnotationFormatString = ""
        customAnnotaionImportClassString = ""
        enableMinimalAnnotation = false

    }

    fun saveState() {
        val newState = State()
        newState.isTestModel = isTestModel
        newState.isCommentOff = isCommentOff
        newState.isPropertiesVar = isPropertiesVar
        newState.targetJsonConvertLib = targetJsonConvertLib
        newState.propertyTypeStrategy = propertyTypeStrategy
        newState.initWithDefaultValue = initWithDefaultValue
        newState.isNestedClassModel = isNestedClassModel

        newState.customPropertyAnnotationFormatString = customPropertyAnnotationFormatString
        newState.customClassAnnotationFormatString = customClassAnnotationFormatString
        newState.customAnnotaionImportClassString = customAnnotaionImportClassString
        newState.enableMinimalAnnotation = enableMinimalAnnotation
        state = newState
    }

    fun restoreState() {
        isTestModel = state.isTestModel
        isCommentOff = state.isCommentOff
        isPropertiesVar = state.isPropertiesVar
        targetJsonConvertLib = state.targetJsonConvertLib
        propertyTypeStrategy = state.propertyTypeStrategy
        initWithDefaultValue = state.initWithDefaultValue
        isNestedClassModel = state.isNestedClassModel
        customPropertyAnnotationFormatString = state.customPropertyAnnotationFormatString
        customClassAnnotationFormatString = state.customClassAnnotationFormatString
        customAnnotaionImportClassString = state.customAnnotaionImportClassString
        enableMinimalAnnotation = state.enableMinimalAnnotation
    }

    class State {
        var isTestModel = false
        var isCommentOff = false
        var isPropertiesVar = false
        var targetJsonConvertLib = TargetJsonConverter.Gson
        var propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        var initWithDefaultValue = true
        var isNestedClassModel = true

        var customPropertyAnnotationFormatString = GsonSupporter.propertyAnnotationFormat
        var customClassAnnotationFormatString = ""
        var customAnnotaionImportClassString = GsonSupporter.annotationImportClassString

        var indent: Int = 4

        var enableMapType: Boolean = true
        var enableAutoReformat: Boolean = true
        var enableMinimalAnnotation = false

    }
}
