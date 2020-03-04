package wu.seal.jsontokotlin.interceptor.annotations.moshi

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

/**
 * This interceptor try to add Moshi(code gen) annotation
 */
class AddMoshiCodeGenAnnotationInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addMoshiCodeGenAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makePropertyName(it.originName, true)
            it.copy(annotations =  MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
        }

        val classAnnotationString = "@JsonClass(generateAdapter = true)"

        val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

        return kotlinDataClass.copy(properties = addMoshiCodeGenAnnotationProperties,annotations = listOf(classAnnotation))
    }
}