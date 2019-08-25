package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.BACKSTAGE_NULLABLE_POSTFIX
import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.getIndent

data class KotlinDataClass(
    val id: Int = -1, // -1 represent the default unknown id
    val annotations: List<Annotation> = listOf(),
    val name: String,
    val properties: List<Property> = listOf(),
    val parentClassTemplate: String = ""
) {

    fun getCode(extraIndent: String = ""): String {
        val indent = getIndent()
        val code = buildString {
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.joinToString("\n") { it.getAnnotationString() }
                if (annotationsCode.isNotBlank()) {
                    append(annotationsCode).append("\n")
                }
            }
            if (properties.isEmpty()) {
                append("class ").append(name).append("(").append("\n")
            } else {
                append("data class ").append(name).append("(").append("\n")
            }
            properties.forEachIndexed { index, property ->
                val code = property.getCode()
                val addIndentCode = code.split("\n").joinToString("\n") { indent + it }
                append(addIndentCode)
                if (index != properties.size - 1) append(",")
                if (property.comment.isNotBlank()) append(" // ").append(getCommentCode(property.comment))
                append("\n")
            }
            append(")")
            if (parentClassTemplate.isNotBlank()) {
                append(" : ")
                append(parentClassTemplate)
            }
            val nestedClasses = properties.mapNotNull { it.typeObject }
            if (nestedClasses.isNotEmpty()) {
                append(" {")
                append("\n")
                val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode(extraIndent = indent) }
                append(nestedClassesCode)
                append("\n")
                append("}")
            }
        }
        return if (extraIndent.isNotEmpty()) {
            code.split("\n").joinToString("\n") {
                if (it.isNotBlank()) {
                    extraIndent + it
                } else {
                    it
                }
            }
        } else {
            code
        }
    }

    fun toParsedKotlinDataClass(): ParsedKotlinDataClass {

        val annotationCodeList = annotations.map { it.getAnnotationString() }

        val parsedProperties = properties.map { it.toParsedProperty() }

        return ParsedKotlinDataClass(annotationCodeList, name, parsedProperties)
    }

    fun applyInterceptors(interceptors: List<IKotlinDataClassInterceptor>): KotlinDataClass {
        val newProperties = mutableListOf<Property>()
        properties.forEach {
            if (it.typeObject != null) {
                newProperties.add(it.copy(typeObject = it.typeObject.applyInterceptors(interceptors)))
            } else {
                newProperties.add(it)
            }
        }
        var newKotlinDataClass = copy(properties = newProperties)
        interceptors.forEach {
            newKotlinDataClass = it.intercept(newKotlinDataClass)
        }
        return newKotlinDataClass
    }

    fun getCurrentClassCode():String {
        val newProperties = properties.map { it.copy(typeObject = null) }
        return copy(properties = newProperties).getCode()
    }

    companion object {

        fun fromParsedKotlinDataClass(parsedKotlinDataClass: ParsedKotlinDataClass): KotlinDataClass {
            val annotations = parsedKotlinDataClass.annotations.map { Annotation.fromAnnotationString(it) }
            val propertyNames = parsedKotlinDataClass.properties.map { it.propertyName }
            val properties = parsedKotlinDataClass.properties
                .map { if (propertyNames.contains(it.propertyName + BACKSTAGE_NULLABLE_POSTFIX)) it.copy(propertyType = it.propertyType + "?") else it }
                .filter { it.propertyName.endsWith(BACKSTAGE_NULLABLE_POSTFIX).not() }
                .map { Property.fromParsedProperty(it) }

            if (properties.isNotEmpty()) {
                properties[properties.lastIndex].isLast = true
            }

            return KotlinDataClass(
                annotations = annotations,
                id = -1,
                name = parsedKotlinDataClass.name,
                properties = properties
            )
        }

    }

}
