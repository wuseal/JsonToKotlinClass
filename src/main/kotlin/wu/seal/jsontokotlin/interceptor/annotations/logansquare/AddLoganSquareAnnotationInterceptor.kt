package wu.seal.jsontokotlin.interceptor.annotations.logansquare

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.codeannotations.LoganSquarePropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

class AddLoganSquareAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            val addLoganSquareAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

                it.copy(annotations =  LoganSquarePropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
            }

            val classAnnotationString = "@JsonObject"

            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)


            return kotlinClass.copy(properties = addLoganSquareAnnotationProperties,annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }
}
