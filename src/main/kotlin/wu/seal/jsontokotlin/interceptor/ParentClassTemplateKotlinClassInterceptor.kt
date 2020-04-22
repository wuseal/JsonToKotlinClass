package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

class ParentClassTemplateKotlinClassInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {

            val parentClassTemplateSimple = ConfigManager.parenClassTemplate.substringAfterLast(".")
            return kotlinClass.copy(parentClassTemplate = parentClassTemplateSimple)
        } else {
            return kotlinClass
        }
    }


}