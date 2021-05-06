package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.*
import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.utils.addIndent
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.getIndent
import wu.seal.jsontokotlin.utils.toAnnotationComments

/**
 * kotlin class code generator
 *
 * Created by Nstd on 2020/6/29 15:40.
 */
data class KotlinCodeBuilder(
        override val name: String,
        override val modifiable: Boolean,
        override val annotations: List<Annotation>,
        override val properties: List<Property>,
        override val parentClassTemplate: String,
        override val comments: String,
        override val fromJsonSchema: Boolean,
        override val excludedProperties: List<String> = listOf(),
        override val parentClass: KotlinClass? = null
): BaseClassCodeBuilder(
                name,
                modifiable,
                annotations,
                properties,
                parentClassTemplate,
                comments,
                fromJsonSchema,
                excludedProperties,
                parentClass
) {

    constructor(clazz: DataClass): this(
            clazz.name,
            clazz.modifiable,
            clazz.annotations,
            clazz.properties,
            clazz.parentClassTemplate,
            clazz.comments,
            clazz.fromJsonSchema,
            clazz.excludedProperties,
            clazz.parentClass
    )

    companion object {
        const val CONF_KOTLIN_IS_DATA_CLASS = "code.builder.kotlin.isDataClass"
        const val CONF_KOTLIN_IS_USE_CONSTRUCTOR_PARAMETER = "code.builder.kotlin.isUseConstructorParameter"
    }

    private var isDataClass: Boolean = true
    private var isUseConstructorParameter: Boolean = true

    val referencedClasses: List<KotlinClass>
        get() {
            return properties.flatMap { property ->
                mutableListOf(property.typeObject).apply {
                    addAll(property.typeObject.getAllGenericsRecursively())
                }
            }
        }

    override fun getOnlyCurrentCode(): String {
        val newProperties = properties.map { it.copy(typeObject = KotlinClass.ANY) }
        return copy(properties = newProperties).getCode()
    }

    override fun getCode(): String {
        isDataClass = getConfig(CONF_KOTLIN_IS_DATA_CLASS, isDataClass)
        isUseConstructorParameter = getConfig(CONF_KOTLIN_IS_USE_CONSTRUCTOR_PARAMETER, isUseConstructorParameter)

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
            val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode() }
            sb.append(nestedClassesCode.addIndent(indent))
            sb.append("\n")
        }
        sb.append("}")
        if (excludedProperties.isNotEmpty()) {
            sb.append(" : ")
            sb.append(parentClass!!.name)
            sb.append("(")
            sb. append(properties.map {
                it.name to it
            }.toMap().filter { excludedProperties.contains(it.key) }
                .map { it.value.inheritanceCode() }.joinToString(", "))
            sb.append(")")
        }
    }

    private fun genJsonFields(sb: StringBuilder, indent: String, isAddSeparator: Boolean) {
        properties.filterNot { excludedProperties.contains(it.name) }.forEachIndexed { index, property ->
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