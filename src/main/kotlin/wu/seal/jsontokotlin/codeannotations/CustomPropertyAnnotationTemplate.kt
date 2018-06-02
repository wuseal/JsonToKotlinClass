package wu.seal.jsontokotlin.codeannotations

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.classscodestruct.Annotation

class CustomPropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    private val annotation = Annotation(ConfigManager.customPropertyAnnotationFormatString, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }

}