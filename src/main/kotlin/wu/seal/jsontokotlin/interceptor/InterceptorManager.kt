package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.ConfigManager

object InterceptorManager {

    fun getEnabledKotlinDataClassInterceptors() :List<IKotlinDataClassInterceptor>{

        val interceptors = mutableListOf<IKotlinDataClassInterceptor>()
        if (ConfigManager.enableMinimalAnnotation) {
            interceptors.add(MinimalAnnotationKotlinDataClassInterceptor())
        }

        if (ConfigManager.parenClassTemplate.isNotBlank()) {
            interceptors.add(ParentClassTemplateKotlinDataClassInterceptor())
        }
        return interceptors
    }


    fun getEnabledImportClassDeclarationInterceptors(): List<IImportClassDeclarationInterceptor>{

        return mutableListOf<IImportClassDeclarationInterceptor>().apply {

            if (ConfigManager.parenClassTemplate.isNotBlank()) {
                add(ParentClassImportClassDeclarationInterceptor())
            }
        }
    }

}