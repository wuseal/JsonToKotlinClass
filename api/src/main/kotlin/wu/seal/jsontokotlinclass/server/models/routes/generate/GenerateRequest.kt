package wu.seal.jsontokotlinclass.server.models.routes.generate

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

open class GenerateRequest(

        @ApiModelProperty(
                value = "JSON text"
        )
        @JsonProperty("json")
        val json: String,

        @ApiModelProperty(
                value = "Output class name",
                example = "Person"
        )
        @JsonProperty("class_name")
        val className: String,

        @ApiModelProperty(
                value = "Annotation library to be used. Default 'None'",
                allowableValues = "None, NoneWithCamelCase, Gson, FastJson, Jackson, MoShi, LoganSquare, MoshiCodeGen, Serializable, Custom"
        )
        @JsonProperty("annotation_lib")
        val annotationLib: String?, // MOSHI_CODE_GEN

        @ApiModelProperty(
                value = "Default value strategy. Default 'None'",
                allowableValues = "None, AvoidNull, AllowNull"
        )
        @JsonProperty("default_value_strategy")
        val defaultValueStrategy: String? = null, // AVOID_NULL


        @ApiModelProperty(
                value = "To set if the properties can be null or not. Default 'AutoDeterMineNullableOrNot'",
                allowableValues = "Nullable, NotNullable, AutoDeterMineNullableOrNot"
        )
        @JsonProperty("property_type_strategy")
        val propertyTypeStrategy: String? = null, // AUTO_DETERMINE_NULLABLE_OR_NOT


        @ApiModelProperty(
                value = "To set indent in output kotlin code. Default '4'"
        )
        @JsonProperty("indent")
        val indent: Int? = null, // 4

        @ApiModelProperty(
                value = "If true, sample data will be appended as a comment to the property declaration line. Default 'false'"
        )
        @JsonProperty("is_comments_enabled")
        val commentsEnabled: Boolean? = null, // true

        @ApiModelProperty(
                value = "If true, annotation will be only created if key and variable are different. Default 'false'"
        )
        @JsonProperty("is_create_annotation_only_when_needed_enabled")
        val createAnnotationOnlyWhenNeededEnabled: Boolean? = null, // true

        @ApiModelProperty(
                value = "If true, all properties will be 'var'. Default 'false'"
        )
        @JsonProperty("is_enable_var_properties")
        val enableVarProperties: Boolean? = null, // false

        @ApiModelProperty(
                value = "If true, property will be initialized with original value. Default 'fasle'"
        )
        @JsonProperty("is_force_init_default_value_with_origin_json_value_enabled")
        val forceInitDefaultValueWithOriginJsonValueEnabled: Boolean? = null, // true

        @JsonProperty("is_force_primitive_type_non_nullable_enabled")
        val forcePrimitiveTypeNonNullableEnabled: Boolean? = null, // true

        @ApiModelProperty(
                value = "If enabled, classes will be nested with in it's parent class. Default 'false'"
        )
        @JsonProperty("is_inner_class_model_enabled")
        val innerClassModelEnabled: Boolean? = null, // false

        @JsonProperty("is_keep_annotation_on_class_androidx_enabled")
        val keepAnnotationOnClassAndroidXEnabled: Boolean? = null, // true

        @JsonProperty("is_keep_annotation_on_class_enabled")
        val keepAnnotationOnClassEnabled: Boolean? = null, // true

        @JsonProperty("is_map_type_enabled")
        val mapTypeEnabled: Boolean? = null, // true

        @ApiModelProperty(
                value = "If enabled properties will be ordered in alphabetic order. Default 'false'"
        )
        @JsonProperty("is_order_by_alphabetic_enabled")
        val orderByAlphabeticEnabled: Boolean? = null, // true

        @JsonProperty("is_parcelable_support_enabled")
        val parcelableSupportEnabled: Boolean? = null, // true

        @JsonProperty("is_property_and_annotation_in_same_line_enabled")
        val propertyAndAnnotationInSameLineEnabled: Boolean? = null, // true

        @JsonProperty("package_name")
        val packageName: String? = null, // com.my.package.name

        @JsonProperty("parent_class_template")
        val parentClassTemplate: String? = null, // android.os.Parcelable

        @JsonProperty("property_prefix")
        val propertyPrefix: String? = null, // MY_PREFIX

        @JsonProperty("class_suffix")
        val classSuffix: String? = null, // MY_CLASS_SUFFIX

        @JsonProperty("property_suffix")
        val propertySuffix: String? = null // MY_SUFFIX
)