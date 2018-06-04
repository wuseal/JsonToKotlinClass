package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.ConfigManager

object InterceptorManager {

    fun getEnabledInterceptors() :List<IInterceptor>{

        val interceptors = mutableListOf<IInterceptor>()
        if (ConfigManager.enableMinimalAnnotation) {
            interceptors.add(MinimalAnnotationInterceptor())
        }

        return interceptors
    }


}