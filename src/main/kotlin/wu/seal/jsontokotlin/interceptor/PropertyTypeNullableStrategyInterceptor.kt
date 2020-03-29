package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.utils.getOutType

class PropertyTypeNullableStrategyInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {

            val propertyTypeAppliedWithNullableStrategyProperties = kotlinClass.properties.map {

                val propertyTypeAppliedWithNullableStrategy = getOutType(it.type, it.originJsonValue)

                it.copy(type = propertyTypeAppliedWithNullableStrategy)
            }

            kotlinClass.copy(properties = propertyTypeAppliedWithNullableStrategyProperties)
        } else {
            kotlinClass
        }

    }

}
