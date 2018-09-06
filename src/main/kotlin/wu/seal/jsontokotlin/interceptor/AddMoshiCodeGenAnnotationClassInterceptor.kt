package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeannotations.MoshiPropertyAnnotationTemplate
import wu.seal.jsontokotlin.codeelements.KPropertyName

/**
 * This interceptor try to add Moshi(code gen) annotation
 */
class AddMoshiCodeGenAnnotationClassInterceptor :IKotlinDataClassInterceptor{

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