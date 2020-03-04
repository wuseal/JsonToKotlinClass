package wu.seal.jsontokotlin.interceptor.annotations.moshi

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddMoshiAnnotationInterceptor : IKotlinDataClassInterceptor {


    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addMoshiCodeGenAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
        }

        return kotlinDataClass.copy(properties = addMoshiCodeGenAnnotationProperties)

    }
}
