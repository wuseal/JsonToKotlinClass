package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass
import wu.seal.jsontokotlin.utils.getIndent

data class KotlinDataClass(
    val id: Int = -1, // -1 represent the default unknown id
    val annotations: List<Annotation>,
    val name: String,
    val properties: List<Property>,
    val nestedClasses: List<KotlinDataClass> = listOf(),
    val parentClassTemplate: String = ""
) {

    fun getCode(extraIndent: String = ""): String {
        val indent = getIndent()
        val code = buildString {
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.map { it.getAnnotationString() }.joinToString("\n")
                if (annotationsCode.isNotBlank()) {
                    append(annotationsCode).append("\n")
                }
            }
            append("data class ").append(name).append("(").append("\n")
            properties.forEach {
                val code = it.getCode()
                val addIndentCode = code.split("\n").joinToString("\n") { indent + it }
                append(addIndentCode)
                if (it.isLast.not()) append(",")
                if (it.comment.isNotBlank()) append(" // ").append(it.comment)
                append("\n")
            }
            append(")")
            if (parentClassTemplate.isNotBlank()) {
                append(" : ")
                append(parentClassTemplate)
            }
            if (nestedClasses.isNotEmpty()) {
                append(" {")
                append("\n")
                val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode(extraIndent = indent) }
                append(nestedClassesCode)
                append("\n")
                append("}")
            }
        }
        if (extraIndent.isNotEmpty()) {
            return code.split("\n").map {
                if (it.isNotBlank()) {
                    extraIndent + it
                } else {
                    it
                }
            }.joinToString("\n")
        } else {
            return code
        }
    }

    fun toParsedKotlinDataClass(): ParsedKotlinDataClass {

        val annotationCodeList = annotations.map { it.getAnnotationString() }

        val parsedProperties = properties.map { it.toParsedProperty() }

        return ParsedKotlinDataClass(annotationCodeList, name, parsedProperties)
    }

    companion object {

        fun fromParsedKotlinDataClass(parsedKotlinDataClass: ParsedKotlinDataClass): KotlinDataClass {
            val annotations = parsedKotlinDataClass.annotations.map { Annotation.fromAnnotationString(it) }
            val properties = parsedKotlinDataClass.properties.map { Property.fromParsedProperty(it) }
            return KotlinDataClass(
                annotations = annotations,
                id = -1,
                name = parsedKotlinDataClass.name,
                properties = properties
            )
        }
    }

}