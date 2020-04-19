package wu.seal.jsontokotlinclass.server.models.routes.addhit

import com.fasterxml.jackson.annotation.JsonProperty
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest

class AddHitRequest(
        @JsonProperty("client")
        val client: String,
        @JsonProperty("generate_meta")
        val generateMeta: GenerateMeta
) {

    class GenerateMeta(
            json: String,
            className: String,
            annotationLib: String,
            classSuffix: String,
            defaultValueStrategy: String,
            propertyTypeStrategy: String,
            indent: Int,
            isCommentsEnabled: Boolean,
            isCreateAnnotationOnlyWhenNeededEnabled: Boolean,
            isEnableVarProperties: Boolean,
            isForceInitDefaultValueWithOriginJsonValueEnabled: Boolean,
            isForcePrimitiveTypeNonNullableEnabled: Boolean,
            isInnerClassModelEnabled: Boolean,
            isKeepAnnotationOnClassAndroidXEnabled: Boolean,
            isKeepAnnotationOnClassEnabled: Boolean,
            isMapTypeEnabled: Boolean,
            isOrderByAlphabeticEnabled: Boolean,
            isParcelableSupportEnabled: Boolean,
            isPropertyAndAnnotationInSameLineEnabled: Boolean,
            packageName: String?,
            parentClassTemplate: String?,
            propertyPrefix: String?,
            propertySuffix: String?
    ) : GenerateRequest(json, className, annotationLib, classSuffix, defaultValueStrategy, propertyTypeStrategy, indent, isCommentsEnabled, isCreateAnnotationOnlyWhenNeededEnabled, isEnableVarProperties, isForceInitDefaultValueWithOriginJsonValueEnabled, isForcePrimitiveTypeNonNullableEnabled, isInnerClassModelEnabled, isKeepAnnotationOnClassAndroidXEnabled, isKeepAnnotationOnClassEnabled, isMapTypeEnabled, isOrderByAlphabeticEnabled, isParcelableSupportEnabled, isPropertyAndAnnotationInSameLineEnabled, packageName, parentClassTemplate, propertyPrefix, propertySuffix)
}