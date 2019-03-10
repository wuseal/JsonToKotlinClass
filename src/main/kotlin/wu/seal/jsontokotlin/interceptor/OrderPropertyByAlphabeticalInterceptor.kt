package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass


class OrderPropertyByAlphabeticalInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val orderByAlphabeticalProperties = kotlinDataClass.properties.sortedBy { it.name }

        return kotlinDataClass.copy(properties = orderByAlphabeticalProperties)
    }

}
