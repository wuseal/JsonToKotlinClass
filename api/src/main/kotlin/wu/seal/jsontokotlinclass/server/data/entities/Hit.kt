package wu.seal.jsontokotlinclass.server.data.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import wu.seal.jsontokotlin.DefaultValueStrategy
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "hits")
class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    lateinit var client: String
    lateinit var className: String
    lateinit var annotationLib: String
    lateinit var defaultValueStrategy: String
    lateinit var propertyTypeStrategy: String

    var classSuffix: String? = null
    var indent: Int? = null
    var isCommentsEnabled: Boolean? = null
    var isCreateAnnotationOnlyWhenNeeded: Boolean? = null
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
}