package wu.seal.jsontokotlin.codeannotations

import wu.seal.jsontokotlin.classscodestruct.Annotation

class SerializablePropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    companion object{

        const val annotationFormat = "@Optional\n@SerialName(\"%s\")"
    }

    private val annotation = Annotation(annotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }
}