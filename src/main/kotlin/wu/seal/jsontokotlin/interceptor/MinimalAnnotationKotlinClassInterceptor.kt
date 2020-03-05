package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

/**
 * interceptor to make the code to be like the minimal annotation
 * which means that if the property name is the same as raw name then remove the
 * annotations contains %s
 */
class MinimalAnnotationKotlinClassInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {
            val newProperties = kotlinClass.properties.map { p ->
                if (p.originName == p.name) {
                    p.copy(annotations = p.annotations.filter { it.rawName.isEmpty() })
                } else {
                    p
                }
            }
            return kotlinClass.copy(properties = newProperties)
        } else {
            return kotlinClass
        }
    }


}
