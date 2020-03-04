package wu.seal.jsontokotlin.interceptor.annotations.custom

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.codeannotations.CustomPropertyAnnotationTemplate
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddCustomAnnotationInterceptor : IKotlinDataClassInterceptor {

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addCustomAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            val annotations = CustomPropertyAnnotationTemplate(it.originName).getAnnotations()

            it.copy(annotations = annotations,name = camelCaseName)
        }

        val classAnnotationString = ConfigManager.customClassAnnotationFormatString

        val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

        return kotlinDataClass.copy(properties = addCustomAnnotationProperties,annotations = listOf(classAnnotation))
    }
}
