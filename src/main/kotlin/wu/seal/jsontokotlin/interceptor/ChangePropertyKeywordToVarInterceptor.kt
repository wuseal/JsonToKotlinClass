package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass

class ChangePropertyKeywordToVarInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is KotlinDataClass) {

            val varProperties = kotlinClass.properties.map {

                it.copy(keyword = "var")
            }

            return kotlinClass.copy(properties = varProperties)
        } else {
            return  kotlinClass
        }
    }

}
