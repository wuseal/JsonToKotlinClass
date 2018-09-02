package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.ConfigManager

object InterceptorManager {

    fun getEnabledKotlinDataClassInterceptors(): List<IKotlinDataClassInterceptor> {

        return mutableListOf<IKotlinDataClassInterceptor>().apply {

            if (ConfigManager.enableMinimalAnnotation) {
                add(MinimalAnnotationKotlinDataClassInterceptor())
            }

            if (ConfigManager.parenClassTemplate.isNotBlank()) {
                add(ParentClassTemplateKotlinDataClassInterceptor())
            }

        }.apply {

            if (size > 1) {
                add(0, MakePropertyOriginNameInterceptor())
            }
        }
    }


    fun getEnabledImportClassDeclarationInterceptors(): List<IImportClassDeclarationInterceptor> {

        return mutableListOf<IImportClassDeclarationInterceptor>().apply {

            if (ConfigManager.parenClassTemplate.isNotBlank()) {
                add(ParentClassImportClassDeclarationInterceptor())
            }
        }
    }

}