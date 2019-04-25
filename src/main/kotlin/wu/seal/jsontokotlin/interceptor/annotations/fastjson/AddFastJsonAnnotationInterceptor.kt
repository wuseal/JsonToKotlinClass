package wu.seal.jsontokotlin.interceptor.annotations.fastjson

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeannotations.FastjsonPropertyAnnotationTemplate
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddFastJsonAnnotationInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addFastJsonAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            val annotations = FastjsonPropertyAnnotationTemplate(it.originName).getAnnotations()

            it.copy(annotations = annotations,name = camelCaseName)
        }

        return kotlinDataClass.copy(properties = addFastJsonAnnotationProperties)
    }

}
