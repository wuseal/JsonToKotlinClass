package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.*
import wu.seal.jsontokotlin.utils.*

/**
 * kotlin class code generator
 *
 * Created by Nstd on 2020/6/29 15:40.
 */
object KotlinDataClassCodeBuilder : IKotlinDataClassCodeBuilder {

    override fun DataClass.genClassComment(): String {
        return buildString {
            append(comments.toAnnotationComments())
        }
    }

    override fun DataClass.genClassAnnotations(): String {
        return buildString {
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.joinToString("\n") { it.getAnnotationString() }
                append(annotationsCode)
            }
        }
    }

    override fun DataClass.genClassName(): String {
        return buildString {
            val primaryConstructorPropertiesCode = genPrimaryConstructorProperties()
            if (primaryConstructorPropertiesCode.isNotEmpty()) {
                append("data ")
            }
            append("class ").append(name)
        }
    }

    override fun DataClass.genParentClass(): String {
        return buildString {
            if (fromJsonSchema) {
                if (!parentClass?.name.isNullOrEmpty()) {
                    append(": ").append(parentClass?.name)
                    if (excludedProperties.isNotEmpty()) {
                        append("(")
                        append(properties.map {
                            it.name to it
                        }.toMap().filter { excludedProperties.contains(it.key) }
                            .map { it.value.inheritanceCode() }.joinToString(", "))
                        append(")")
                    }
                }
            } else {
                if (parentClassTemplate.isNotEmpty()) {
                    append(": ").append(parentClassTemplate)
                }
            }
        }
    }

    override fun DataClass.genBody(): String {
        return buildString {
            val nestedClasses = referencedClasses.filter { it.modifiable }
            if (nestedClasses.isEmpty()) return@buildString
            val nestedClassesCode = nestedClasses.map { it.getCode() }.filter { it.isNotBlank() }.joinToString("\n\n")
            return nestedClassesCode.addIndent(indent)
        }
    }

    override fun DataClass.genPrimaryConstructorProperties(): String {
        return buildString {
            properties.filterNot { excludedProperties.contains(it.name) }.forEachIndexed { index, property ->
                val propertyCode = property.getCode().addIndent(indent)
                val commentCode = getCommentCode(property.comment)
                if (fromJsonSchema && commentCode.isNotBlank()) {
                    append(commentCode.toAnnotationComments(indent))
                }
                append(propertyCode)
                if (index != properties.size - 1) append(",")
                if (!fromJsonSchema && commentCode.isNotBlank()) append(" // ").append(commentCode)
                append("\n")
            }
        }
    }
}
