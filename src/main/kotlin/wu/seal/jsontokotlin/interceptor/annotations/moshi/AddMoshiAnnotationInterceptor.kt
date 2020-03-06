package wu.seal.jsontokotlin.interceptor.annotations.moshi

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class AddMoshiAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }

            return kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties)
        } else {
            return kotlinClass
        }

    }
}
