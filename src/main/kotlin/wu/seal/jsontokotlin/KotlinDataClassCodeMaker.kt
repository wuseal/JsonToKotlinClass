package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.interceptor.IInterceptor
import wu.seal.jsontokotlin.interceptor.MinimalAnnotationInterceptor

class KotlinDataClassCodeMaker(private val rootClassName: String, private val json: String) {

    fun makeKotlinDataClassCode(): String {
        return if (needMakeKotlinCodeByKotlinDataClass()) {
            val interceptors = mutableListOf<IInterceptor>()
            if (ConfigManager.enableMinimalAnnotation) {
                interceptors.add(MinimalAnnotationInterceptor())
            }
            makeKotlinDataClassCode(interceptors)
        } else {
            KotlinCodeMaker(rootClassName, json).makeKotlinData()
        }
    }

    private fun needMakeKotlinCodeByKotlinDataClass(): Boolean {
        return ConfigManager.enableMinimalAnnotation
    }

    fun makeKotlinDataClassCode(interceptors: List<IInterceptor>): String {
        val kotlinDataClasses = KotlinDataClassMaker(rootClassName = rootClassName, json = json).makeKotlinDataClasses()
        val interceptedDataClasses = kotlinDataClasses.map {
            var kotlinDataClass = it
            interceptors.forEach {
                kotlinDataClass = it.intercept(kotlinDataClass)
            }
            kotlinDataClass
        }

        val code = interceptedDataClasses.joinToString("\n\n") {
            it.getCode()
        }
        return code
    }
}