package wu.seal.jsontokotlinclass.server.models.routes.addhit

import com.fasterxml.jackson.annotation.JsonProperty
import wu.seal.jsontokotlin.test.TestConfig
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest

class AddHitRequest(
        @JsonProperty("client")
        val client: String,
        @JsonProperty("generate_meta")
        val generateMeta: GenerateMeta
) {

    class GenerateMeta(
            @JsonProperty("json")
            json: String,

            @JsonProperty("class_name")
            className: String,

            @JsonProperty("annotation_lib")
            annotationLib: String,

            @JsonProperty("default_value_strategy")
            defaultValueStrategy: String,

            @JsonProperty("property_type_strategy")
            propertyTypeStrategy: String,

            @JsonProperty("indent")
            indent: Int,

            @JsonProperty("is_comments_enabled")
            isCommentsEnabled: Boolean,

            @JsonProperty("is_create_annotation_only_when_needed_enabled")
            isCreateAnnotationOnlyWhenNeededEnabled: Boolean,

            @JsonProperty("is_enable_var_properties")
            isEnableVarProperties: Boolean,

            @JsonProperty("is_force_init_default_ue_with_origin_json_ue_enabled")
            isForceInitDefaultValueWithOriginJsonValueEnabled: Boolean,

            @JsonProperty("is_force_primitive_type_non_nullable_enabled")
            isForcePrimitiveTypeNonNullableEnabled: Boolean,

            @JsonProperty("is_inner_class_model_enabled")
            isInnerClassModelEnabled: Boolean,

            @JsonProperty("is_keep_annotation_on_class_androidx_enabled")
            isKeepAnnotationOnClassAndroidXEnabled: Boolean,

            @JsonProperty("is_keep_annotation_on_class_enabled")
            isKeepAnnotationOnClassEnabled: Boolean,

            @JsonProperty("is_map_type_enabled")
            isMapTypeEnabled: Boolean,

            @JsonProperty("is_order_by_alphabetic_enabled")
            isOrderByAlphabeticEnabled: Boolean,

            @JsonProperty("is_parcelable_support_enabled")
            isParcelableSupportEnabled: Boolean,

            @JsonProperty("is_property_and_annotation_in_same_line_enabled")
            isPropertyAndAnnotationInSameLineEnabled: Boolean,

            @JsonProperty("package_name")
            packageName: String?,

            @JsonProperty("parent_class_template")
            parentClassTemplate: String?,

            @JsonProperty("property_prefix")
            propertyPrefix: String?,

            @JsonProperty("class_suffix")
            classSuffix: String?,

            @JsonProperty("property_suffix")
            propertySuffix: String?
    ) : GenerateRequest(
            json,
            className,
            annotationLib,
            defaultValueStrategy,
            propertyTypeStrategy,
            indent,
            isCommentsEnabled,
            isCreateAnnotationOnlyWhenNeededEnabled,
            isEnableVarProperties,
            isForceInitDefaultValueWithOriginJsonValueEnabled,
            isForcePrimitiveTypeNonNullableEnabled,
            isInnerClassModelEnabled,
            isKeepAnnotationOnClassAndroidXEnabled,
            isKeepAnnotationOnClassEnabled,
            isMapTypeEnabled,
            isOrderByAlphabeticEnabled,
            isParcelableSupportEnabled,
            isPropertyAndAnnotationInSameLineEnabled,
            packageName,
            parentClassTemplate,
            propertyPrefix,
            classSuffix,
            propertySuffix
    )
}