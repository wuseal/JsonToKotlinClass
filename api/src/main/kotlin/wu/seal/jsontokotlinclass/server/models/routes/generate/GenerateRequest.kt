package wu.seal.jsontokotlinclass.server.models.routes.generate

import com.fasterxml.jackson.annotation.JsonProperty

//TODO: Set default values for generate request
open class GenerateRequest(
        @JsonProperty("json")
        val json: String,

        @JsonProperty("class_name")
        val className: String,

        @JsonProperty("annotation_lib")
        val annotationLib: String?, // MOSHI_CODE_GEN

        @JsonProperty("class_suffix")
        val classSuffix: String? = null, // MY_CLASS_SUFFIX

        @JsonProperty("default_value_strategy")
        val defaultValueStrategy: String? = null, // AVOID_NULL

        @JsonProperty("property_type_strategy")
        val propertyTypeStrategy: String? = null, // AUTO_DETERMINE_NULLABLE_OR_NOT

        @JsonProperty("indent")
        val indent: Int? = null, // 3

        @JsonProperty("is_comments_enabled")
        val isCommentsEnabled: Boolean? = null, // true

        @JsonProperty("is_create_annotation_only_when_needed_enabled")
        val isCreateAnnotationOnlyWhenNeededEnabled: Boolean? = null, // true

        @JsonProperty("is_enable_var_properties")
        val isEnableVarProperties: Boolean? = null, // false

        @JsonProperty("is_force_init_default_value_with_origin_json_value_enabled")
        val isForceInitDefaultValueWithOriginJsonValueEnabled: Boolean? = null, // true

        @JsonProperty("is_force_primitive_type_non_nullable_enabled")
        val isForcePrimitiveTypeNonNullableEnabled: Boolean? = null, // true

        @JsonProperty("is_inner_class_model_enabled")
        val isInnerClassModelEnabled: Boolean? = null, // false

        @JsonProperty("is_keep_annotation_on_class_androidx_enabled")
        val isKeepAnnotationOnClassAndroidXEnabled: Boolean? = null, // true

        @JsonProperty("is_keep_annotation_on_class_enabled")
        val isKeepAnnotationOnClassEnabled: Boolean? = null, // true

        @JsonProperty("is_map_type_enabled")
        val isMapTypeEnabled: Boolean? = null, // true

        @JsonProperty("is_order_by_alphabetic_enabled")
        val isOrderByAlphabeticEnabled: Boolean? = null, // true

        @JsonProperty("is_parcelable_support_enabled")
        val isParcelableSupportEnabled: Boolean? = null, // true

        @JsonProperty("is_property_and_annotation_in_same_line_enabled")
        val isPropertyAndAnnotationInSameLineEnabled: Boolean? = null, // true

        @JsonProperty("package_name")
        val packageName: String? = null, // com.my.package.name

        @JsonProperty("parent_class_template")
        val parentClassTemplate: String? = null, // android.os.Parcelable

        @JsonProperty("property_prefix")
        val propertyPrefix: String? = null, // MY_PREFIX

        @JsonProperty("property_suffix")
        val propertySuffix: String? = null // MY_SUFFIX
)