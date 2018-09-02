package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

/**
 * Use this try to recover the origin name that before property name formatting to camel case
 */
class MakePropertyOriginNameInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val makeUpOriginNameProperties = kotlinDataClass.properties.map {
            val rawName = it.getRawName()
            return@map if (rawName.isEmpty()) {
                it.copy(originName = it.name)
            } else {
                it.copy(originName = rawName)
            }
        }

        return kotlinDataClass.copy(properties = makeUpOriginNameProperties)
    }
}