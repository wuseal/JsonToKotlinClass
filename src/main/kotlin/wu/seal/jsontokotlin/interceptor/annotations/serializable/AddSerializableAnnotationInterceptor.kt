package wu.seal.jsontokotlin.interceptor.annotations.serializable

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.model.codeannotations.SerializablePropertyAnnotationTemplate
import wu.seal.jsontokotlin.model.codeelements.KPropertyName
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor

class AddSerializableAnnotationInterceptor:IKotlinDataClassInterceptor{

    override fun intercept(kotlinDataClass: KotlinDataClass): KotlinDataClass {

        val addCustomAnnotationProperties = kotlinDataClass.properties.map {

            val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)

            val annotations = SerializablePropertyAnnotationTemplate(it.originName).getAnnotations()

            it.copy(annotations = annotations,name = camelCaseName)
        }

        val classAnnotationString = "@Serializable"

        val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

        return kotlinDataClass.copy(properties = addCustomAnnotationProperties,annotations = listOf(classAnnotation))
    }

}