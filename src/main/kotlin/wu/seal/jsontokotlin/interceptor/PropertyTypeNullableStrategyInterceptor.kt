package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.getOutType

class PropertyTypeNullableStrategyInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val propertyTypeAppliedWithNullableStrategyProperties = kotlinDataClass.properties.map {

            val propertyTypeAppliedWithNullableStrategy = getOutType(it.type, it.originJsonValue)

            it.copy(type = propertyTypeAppliedWithNullableStrategy)
        }

        return kotlinDataClass.copy(properties = propertyTypeAppliedWithNullableStrategyProperties)
    }

}
