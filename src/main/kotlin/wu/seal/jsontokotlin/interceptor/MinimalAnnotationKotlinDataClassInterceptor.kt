package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass

/**
 * interceptor to make the code to be like the minimal annotation
 * which means that if the property name is the same as raw name then remove the
 * annotations contains %s
 */
class MinimalAnnotationKotlinDataClassInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        val newProperties = kotlinDataClass.properties.map { p ->
            if (p.originName == p.name) {
                p.copy(annotations = p.annotations.filter { it.rawName.isEmpty() })
            } else {
                p
            }
        }

        return kotlinDataClass.copy(properties = newProperties)

    }


}
