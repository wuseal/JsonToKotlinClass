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
    var isInnerClassModel = true

    var customPropertyAnnotationFormatString = GsonSupporter.propertyAnnotationFormat
    var customClassAnnotationFormatString = ""
    var customAnnotaionImportClassString = GsonSupporter.annotationImportClassString

    var indent: Int = 4

    var enableMapType: Boolean = true
    var enableAutoReformat: Boolean = true

    fun setToTestInitState() {
        isTestModel = true
        isCommentOff = false
        isPropertiesVar = false
        targetJsonConvertLib = TargetJsonConverter.Gson
        propertyTypeStrategy = PropertyTypeStrategy.NotNullable
        initWithDefaultValue = true
        isInnerClassModel = true
        customPropertyAnnotationFormatString = GsonSupporter.propertyAnnotationFormat
        customClassAnnotationFormatString = ""
        customAnnotaionImportClassString = GsonSupporter.annotationImportClassString
    }
}
