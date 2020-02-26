package wu.seal.jsontokotlin.interceptor.annotations.logansquare

import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeannotations.LoganSquarePropertyAnnotationTemplate
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddLoganSquareAnnotationInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addLoganSquareAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            it.copy(annotations =  LoganSquarePropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
        }

        val classAnnotationString = "@JsonObject"

        val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)


        return kotlinDataClass.copy(properties = addLoganSquareAnnotationProperties,annotations = listOf(classAnnotation))
    }
}
