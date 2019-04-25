package wu.seal.jsontokotlin.codeannotations

import wu.seal.jsontokotlin.classscodestruct.Annotation
import wu.seal.jsontokotlin.supporter.LoganSquareSupporter

class LoganSquarePropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    private val propertyAnnotationFormat = "@JsonField(name = arrayOf(\"%s\"))"

    private val annotation = Annotation(propertyAnnotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }
}
