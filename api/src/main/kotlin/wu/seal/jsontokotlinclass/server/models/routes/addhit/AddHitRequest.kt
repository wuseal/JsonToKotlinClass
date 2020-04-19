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
            @JsonProperty("json")
            json: String,

            @JsonProperty("class_name")
            className: String,

            @JsonProperty("annotation_lib")
            annotationLib: String, // MOSHI_CODE_GEN

            @JsonProperty("class_suffix")
            classSuffix: String, // MY_CLASS_SUFFIX

            @JsonProperty("default_ue_strategy")
            defaultValueStrategy: String, // AVOID_NULL

            @JsonProperty("property_type_strategy")
            propertyTypeStrategy: String, // AUTO_DETERMINE_NULLABLE_OR_NOT

            @JsonProperty("indent")
            indent: Int, // 3

            @JsonProperty("is_comments_enabled")
            isCommentsEnabled: Boolean, // true

            @JsonProperty("is_create_annotation_only_when_needed_enabled")
            isCreateAnnotationOnlyWhenNeededEnabled: Boolean, // true

            @JsonProperty("is_enable_var_properties")
            isEnableVarProperties: Boolean, // false

            @JsonProperty("is_force_init_default_ue_with_origin_json_ue_enabled")
            isForceInitDefaultValueWithOriginJsonValueEnabled: Boolean, // true

            @JsonProperty("is_force_primitive_type_non_nullable_enabled")
            isForcePrimitiveTypeNonNullableEnabled: Boolean, // true

            @JsonProperty("is_inner_class_model_enabled")
            isInnerClassModelEnabled: Boolean, // false

            @JsonProperty("is_keep_annotation_on_class_androidx_enabled")
            isKeepAnnotationOnClassAndroidXEnabled: Boolean, // true

            @JsonProperty("is_keep_annotation_on_class_enabled")
            isKeepAnnotationOnClassEnabled: Boolean, // true

            @JsonProperty("is_map_type_enabled")
            isMapTypeEnabled: Boolean, // true

            @JsonProperty("is_order_by_alphabetic_enabled")
            isOrderByAlphabeticEnabled: Boolean, // true

            @JsonProperty("is_parcelable_support_enabled")
            isParcelableSupportEnabled: Boolean, // true

            @JsonProperty("is_property_and_annotation_in_same_line_enabled")
            isPropertyAndAnnotationInSameLineEnabled: Boolean, // true

            @JsonProperty("package_name")
            packageName: String?, // com.my.package.name

            @JsonProperty("parent_class_template")
            parentClassTemplate: String?, // android.os.Parcelable

            @JsonProperty("property_prefix")
            propertyPrefix: String?, // MY_PREFIX

            @JsonProperty("property_suffix")
            propertySuffix: String? // MY_SUFFIX
    ) : GenerateRequest(
            json,
            className,
            annotationLib,
            classSuffix,
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
            propertySuffix
    )
}