package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.model.classscodestruct.Property
import wu.seal.jsontokotlin.utils.addIndent
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.getIndent
import wu.seal.jsontokotlin.utils.toAnnotationComments

/**
 * kotlin code generator
 *
 * Created by Nstd on 2020/6/29 15:40.
 */
class KotlinCodeBuilder(
        _name: String,
        _annotations: List<Annotation> = listOf(),
        _properties: List<Property> = listOf(),
        _parentClassTemplate: String = "",
        _modifiable: Boolean = true,
        _comments: String = "",
        _fromJsonSchema: Boolean = false
        ): BaseCodeBuilder(
                _name,
                _annotations,
                _properties,
                _parentClassTemplate,
                _modifiable,
                _comments,
                _fromJsonSchema) {

    /**
     * set false to user Normal class
     */
    var isDataClass = true
    /**
     * set false to use class member variable
     */
    var isUseConstructorParameter = true

    override fun getCode(): String {
        if (fromJsonSchema && properties.isEmpty()) return ""
        return buildString {
            append(comments.toAnnotationComments())
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.joinToString("\n") { it.getAnnotationString() }
                if (annotationsCode.isNotBlank()) {
                    append(annotationsCode).append("\n")
                }
            }

            genClassTitle(this)
            genConstructor(this)
            genParentClass(this)
            genBody(this)
        }
    }

    private fun genClassTitle(sb: StringBuilder) {
        if(isDataClass && properties.isNotEmpty()) {
            sb.append("data ")
        }
        sb.append("class ").append(name)
    }

    private fun genConstructor(sb: StringBuilder) {
        if(isUseConstructorParameter) {
            sb.append("(").append("\n")
            genJsonFields(sb, getIndent(), true)
            sb.append(")")
        }
    }

    private fun genParentClass(sb: StringBuilder) {
        if (parentClassTemplate.isNotBlank()) {
            sb.append(" : ").append(parentClassTemplate)
        }
    }

    private fun genBody(sb: StringBuilder) {
        val nestedClasses = referencedClasses.filter { it.modifiable }
        val hasMember = !isUseConstructorParameter && properties.isNotEmpty()
        if(!hasMember && nestedClasses.isEmpty()) return
        val indent = getIndent()
        sb.append(" {").append("\n")
        if(hasMember) {
            genJsonFields(sb, indent, false)
        }
        if(nestedClasses.isNotEmpty()) {
            sb.append("\n")
            val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode() }
            sb.append(nestedClassesCode.addIndent(indent))
            sb.append("\n")
        }
        sb.append("}")
    }

    private fun genJsonFields(sb: StringBuilder, indent: String, isAddSeparator: Boolean) {
        properties.forEachIndexed { index, property ->
            val addIndentCode = property.getCode().addIndent(indent)
            val commentCode = getCommentCode(property.comment)
            if (fromJsonSchema && commentCode.isNotBlank()) {
                sb.append(commentCode.toAnnotationComments(indent))
            }
            sb.append(addIndentCode)
            if (index != properties.size - 1 && isAddSeparator) sb.append(",")
            if (!fromJsonSchema && commentCode.isNotBlank()) sb.append(" // ").append(commentCode)
            sb.append("\n")
        }
    }

}