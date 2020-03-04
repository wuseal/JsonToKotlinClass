package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass

class ParentClassTemplateKotlinDataClassInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val parentClassTemplateSimple = ConfigManager.parenClassTemplate.substringAfterLast(".")
        return kotlinDataClass.copy(parentClassTemplate = parentClassTemplateSimple)
    }


}