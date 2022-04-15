package extensions.wu.seal

import wu.seal.jsontokotlin.model.builder.IKotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Created by Nstd on 2021/8/6 11:17.
 */
abstract class BaseDataClassCodeBuilder(private val kotlinDataClassCodeBuilder: IKotlinDataClassCodeBuilder) :
    IKotlinDataClassCodeBuilder by kotlinDataClassCodeBuilder {

    open fun DataClass.genPrimaryConstructor(): String {
        return buildString {
            val primaryConstructorPropertiesCode = genPrimaryConstructorProperties()
            if (primaryConstructorPropertiesCode.isNotBlank()) {
                append("(").append("\n")
                append(primaryConstructorPropertiesCode)
                append(")")
            }
        }
    }

    final override fun getCode(clazz: DataClass): String {
        clazz.run {
            return buildString {
                genClassComment().executeWhenNotBlank { append(it) }
                genClassAnnotations().executeWhenNotBlank { appendLine(it) }
                append(genClassName())
                genPrimaryConstructor().executeWhenNotBlank { append(it) }
                genParentClass().executeWhenNotBlank { append(" $it") }
                genBody().executeWhenNotBlank {
                    appendLine(" {")
                    appendLine(it)
                    append("}")
                }
            }
        }
    }

    final override fun getOnlyCurrentCode(clazz: DataClass): String {
        clazz.run {
            val newProperties = properties.map { it.copy(typeObject = KotlinClass.ANY) }
            return copy(properties = newProperties).getCode()
        }
    }
}