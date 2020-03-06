package wu.seal.jsontokotlin.interceptor.annotations.moshi

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * This interceptor try to add Moshi(code gen) annotation
 */
class AddMoshiCodeGenAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {

                val camelCaseName = KPropertyName.makePropertyName(it.originName, true)
                it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            val classAnnotationString = "@JsonClass(generateAdapter = true)"

            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

            return kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties, annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }
}