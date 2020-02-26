package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeelements.KPropertyName

class MakePropertiesNameToBeCamelCaseInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val camelCaseNameProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalNameOrEmptyName(it.originName)

            it.copy(name = camelCaseName)
        }

        return kotlinDataClass.copy(properties = camelCaseNameProperties)
    }

}
