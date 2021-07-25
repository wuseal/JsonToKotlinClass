package extensions.nstd

import wu.seal.jsontokotlin.model.builder.IKotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.utils.addIndent
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.toAnnotationComments

/**
 * kotlin class code generator
 *
 * Created by Nstd on 2020/6/29 15:40.
 */
class DataClassCodeBuilderForNoConstructorMemberFields(private val kotlinDataClassCodeBuilder: IKotlinDataClassCodeBuilder) :
    IKotlinDataClassCodeBuilder {

    override fun DataClass.genBody(): String {
        val delegateBody = kotlinDataClassCodeBuilder.run { genBody() }
        val noConstructorMemberFields = genNoConstructorProperties()
        return buildString {
            if (delegateBody.isEmpty()) {
                append(noConstructorMemberFields)
            } else{
                appendLine(noConstructorMemberFields)
                append(delegateBody)
            }
        }
    }

    override fun DataClass.genClassComment(): String {
        return kotlinDataClassCodeBuilder.run { genClassComment() }
    }

    override fun DataClass.genClassAnnotations(): String {
        return kotlinDataClassCodeBuilder.run { genClassAnnotations() }
    }

    override fun DataClass.genClassName(): String {
        return kotlinDataClassCodeBuilder.run { genClassName() }
    }


    override fun DataClass.genParentClass(): String {
        return kotlinDataClassCodeBuilder.run { genParentClass() }
    }

    override fun DataClass.genPrimaryConstructorProperties(): String {
        return ""
    }

    private fun DataClass.genNoConstructorProperties(): String {
        return buildString {
            properties.filterNot { excludedProperties.contains(it.name) }.forEachIndexed { index, property ->
                val addIndentCode = property.getCode().addIndent(indent)
                val commentCode = getCommentCode(property.comment)
                if (fromJsonSchema && commentCode.isNotBlank()) {
                    append(commentCode.toAnnotationComments(indent))
                }
                append(addIndentCode)
                if (!fromJsonSchema && commentCode.isNotBlank()) append(" // ").append(commentCode)
            }
        }
    }
}
