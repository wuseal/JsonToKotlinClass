package wu.seal.jsontokotlin.test

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
    var isPropertyNullable = true
    var initWithDefaultValue = true
    var isInnerClassModel = true

    var customPropertyAnnotationFormatString = GsonSupporter.propertyAnnotationFormat
    var customClassAnnotationFormatString = ""
    var customAnnotaionImportClassString = GsonSupporter.annotationImportClassString

    fun resetToTestInitState() {
        isTestModel = true
        isCommentOff = false
        isPropertiesVar = false
        targetJsonConvertLib = TargetJsonConverter.Gson
        isPropertyNullable = true
        initWithDefaultValue = true
        isInnerClassModel = true
        customPropertyAnnotationFormatString = GsonSupporter.propertyAnnotationFormat
        customClassAnnotationFormatString = ""
        customAnnotaionImportClassString = GsonSupporter.annotationImportClassString
    }
}
