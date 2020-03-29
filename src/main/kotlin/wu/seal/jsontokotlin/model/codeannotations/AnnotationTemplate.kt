package wu.seal.jsontokotlin.model.codeannotations

import wu.seal.jsontokotlin.model.classscodestruct.Annotation

interface AnnotationTemplate {
    fun getCode():String
    fun getAnnotations():List<Annotation>
}