package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

/**
 * interceptor to make the code to be like the minimal annotation
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

        return if (kotlinDataClass.nestedClasses.isNotEmpty()) {

            val newNestedClasses = kotlinDataClass.nestedClasses.map { intercept(it) }
            kotlinDataClass.copy(properties = newProperties, nestedClasses = newNestedClasses)

        } else {
            kotlinDataClass.copy(properties = newProperties)
        }

    }


}