package wu.seal.jsontokotlin.interceptor.annotations.gson

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeannotations.GsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddGsonAnnotationInterceptor : IKotlinDataClassInterceptor {


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {


        val addGsonAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            it.copy(annotations =  GsonPropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
        }


        return kotlinDataClass.copy(properties = addGsonAnnotationProperties)
    }

}
