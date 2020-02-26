package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.classscodestruct.Property


class OrderPropertyByAlphabeticalInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val orderByAlphabeticalProperties = kotlinDataClass.properties.sortedBy { it.name }

        return kotlinDataClass.copy(properties = orderByAlphabeticalProperties)
    }
}

