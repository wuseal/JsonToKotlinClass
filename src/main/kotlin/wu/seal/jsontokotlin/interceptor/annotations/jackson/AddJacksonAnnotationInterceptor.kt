package wu.seal.jsontokotlin.interceptor.annotations.jackson

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeannotations.JacksonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor


class AddJacksonAnnotationInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addMoshiCodeGenAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            it.copy(annotations =  JacksonPropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
        }

        return kotlinDataClass.copy(properties = addMoshiCodeGenAnnotationProperties)

    }

}
