package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass

class ChangePropertyKeywordToVarInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val varProperties = kotlinDataClass.properties.map {

            it.copy(keyword = "var")
        }

        return kotlinDataClass.copy(properties = varProperties)
    }

}
