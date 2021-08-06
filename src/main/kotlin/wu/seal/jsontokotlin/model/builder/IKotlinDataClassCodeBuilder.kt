package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

interface IKotlinDataClassCodeBuilder : ICodeBuilder<DataClass> {
    fun DataClass.genClassComment(): String
    fun DataClass.genClassAnnotations(): String
    fun DataClass.genClassName(): String
    fun DataClass.genParentClass(): String
    fun DataClass.genBody(): String
    fun DataClass.genPrimaryConstructorProperties(): String

    fun genPrimaryConstructor(clazz: DataClass): String {
        return clazz.run {
            buildString {
                val primaryConstructorPropertiesCode = genPrimaryConstructorProperties()
                if (primaryConstructorPropertiesCode.isNotBlank()) {
                    append("(").append("\n")
                    append(primaryConstructorPropertiesCode)
                    append(")")
                }
            }
        }
    }

    override fun getCode(clazz: DataClass): String {
        clazz.run {
            return buildString {
                genClassComment().executeWhenNotBlank { append(it) }
                genClassAnnotations().executeWhenNotBlank { appendLine(it) }
                append(genClassName())
                genPrimaryConstructor(this@run).executeWhenNotBlank { append(it) }
                genParentClass().executeWhenNotBlank { append(" $it") }
                genBody().executeWhenNotBlank {
                    appendLine(" {")
                    appendLine(it)
                    append("}")
                }
            }
        }
    }

    override fun getOnlyCurrentCode(clazz: DataClass): String {
        clazz.run {
            val newProperties = properties.map { it.copy(typeObject = KotlinClass.ANY) }
            return copy(properties = newProperties).getCode()
        }
    }

    fun String.executeWhenNotBlank(call: (String) -> Unit) = if (isNotBlank()) call(this) else Unit

}