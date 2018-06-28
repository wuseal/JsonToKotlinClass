package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

/**
 * interceptor to make the code to be like the minimal annotation
 * which means that if the property name is the same as raw name then remove the
 * annotations contains %s
 */
class MinimalAnnotationKotlinDataClassInterceptor() : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {
        val newProperties = kotlinDataClass.properties.map {
            if (it.annotations.isNotEmpty()) {
                val rawName = it.getRawName()
                if (rawName.isEmpty()) {
                    it
                } else {
                    if (rawName == it.name) {
                        it.copy(annotations = it.annotations.filter {
                            it.rawName.isBlank()
                        })
                    } else {
                        it
                    }
                }
            } else {
                it
            }
        }

        return kotlinDataClass.copy(properties = newProperties)

    }


}