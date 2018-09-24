package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.KOTLIN_KEYWORD_LIST

/**
 * Interceptor to make kotlin keyword property names valid
 */
class MakeKeywordNamedPropertyValidInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val keywordValidProperties = kotlinDataClass.properties.map {
            if (KOTLIN_KEYWORD_LIST.contains(it.name)) {
                it.copy(name = "`${it.name}`")
            } else {
                it
            }
        }

        return kotlinDataClass.copy(properties = keywordValidProperties)
    }
}
