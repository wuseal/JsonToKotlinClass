package wu.seal.jsontokotlin.interceptor.annotations.gson

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeannotations.GsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class AddGsonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is KotlinDataClass) {
            val addGsonAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations = GsonPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            kotlinClass.copy(properties = addGsonAnnotationProperties)
        } else {
            kotlinClass
        }

    }

}
