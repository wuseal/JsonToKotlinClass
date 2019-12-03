package wu.seal.jsontokotlin.codeannotations

import wu.seal.jsontokotlin.classscodestruct.Annotation

class JacksonPropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    companion object{

        const val annotationFormat = "@JsonProperty(\"%s\")"
    }

    private val annotation = Annotation(annotationFormat, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }
}