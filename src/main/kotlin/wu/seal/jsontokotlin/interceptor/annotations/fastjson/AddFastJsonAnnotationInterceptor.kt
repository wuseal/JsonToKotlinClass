package wu.seal.jsontokotlin.interceptor.annotations.fastjson

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.codeannotations.FastjsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

class AddFastJsonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {

            val addFastJsonAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                val annotations = FastjsonPropertyAnnotationTemplate(it.originName).getAnnotations()

                it.copy(annotations = annotations, name = camelCaseName)
            }

            kotlinClass.copy(properties = addFastJsonAnnotationProperties)
        } else {
            kotlinClass
        }

    }

}
