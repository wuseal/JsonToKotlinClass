package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass


class OrderPropertyByAlphabeticalInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is KotlinDataClass) {

            val orderByAlphabeticalProperties = kotlinClass.properties.sortedBy { it.name }

            return kotlinClass.copy(properties = orderByAlphabeticalProperties)
        } else {
            return kotlinClass
        }

    }
}

