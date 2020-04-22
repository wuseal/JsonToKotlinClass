package wu.seal.jsontokotlinclass.server.data.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "hits")
class Hit {

    companion object {
        const val CLIENT_PLUG_IN = "PLUG_IN"
        const val CLIENT_WEB = "WEB"
        const val CLIENT_API = "API"

        const val A_LIB_NONE = "NONE"
        const val A_LIB_NONE_CC = "NONE_CC"
        const val A_LIB_GSON = "GSON"
        const val A_LIB_FAST_JSON = "FAST_JSON"
        const val A_LIB_JACKSON = "JACKSON"
        const val A_LIB_MOSHI = "MOSHI"
        const val A_LIB_LOGAN_SQUARE = "LOGAN_SQUARE"
        const val A_LIB_MOSHI_CODE_GEN = "MOSHI_CODE_GEN"
        const val A_LIB_SERIALIZABLE = "SERIALIZABLE"
        const val A_LIB_CUSTOM = "CUSTOM"

        const val DVS_AVOID_NULL = "AVOID_NULL"
        const val DVS_ALLOW_NULL = "ALLOW_NULL"
        const val DVS_NONE = "NONE"

        const val PTS_NOT_NULLABLE = "NOT_NULLABLE"
        const val PTS_NULLABLE = "NULLABLE"
        const val PTS_AUTO_DETERMINE_NULLABLE_OR_NOT = "AUTO_DETERMINE_NULLABLE_OR_NOT"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    lateinit var client: String
    lateinit var className: String
    lateinit var annotationLib: String
    lateinit var defaultValueStrategy: String
    lateinit var propertyTypeStrategy: String
    var indent: Int? = null
    var isCommentsEnabled: Boolean? = null
    var isCreateAnnotationOnlyWhenNeededEnabled: Boolean? = null
    var isEnableVarProperties: Boolean? = null
    var isForceInitDefaultValueWithOriginJsonValueEnabled: Boolean? = null
    var isForcePrimitiveTypeNonNullableEnabled: Boolean? = null
    var isInnerClassModelEnabled: Boolean? = null
    var isKeepAnnotationOnClassAndroidxEnabled: Boolean? = null
    var isKeepAnnotationOnClassEnabled: Boolean? = null
    var isMapTypeEnabled: Boolean? = null
    var isOrderByAlphabeticEnabled: Boolean? = null
    var isParcelableSupportEnabled: Boolean? = null
    var isPropertyAndAnnotationInSameLineEnabled: Boolean? = null

    var packageName: String? = null
    var parentClassTemplate: String? = null
    var propertyPrefix: String? = null
    var propertySuffix: String? = null
    var classSuffix: String? = null
}