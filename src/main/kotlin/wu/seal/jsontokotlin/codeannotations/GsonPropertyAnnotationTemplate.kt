package wu.seal.jsontokotlin.codeannotations

import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.supporter.GsonSupporter

class GsonPropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    private val annotation = Annotation(GsonSupporter.propertyAnnotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }
}