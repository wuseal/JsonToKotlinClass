package wu.seal.jsontokotlin.model.builder

import extensions.wu.seal.InternalModifierSupport
import wu.seal.jsontokotlin.model.classscodestruct.*
import wu.seal.jsontokotlin.model.classscodestruct.Annotation
import wu.seal.jsontokotlin.utils.*

/**
 * kotlin class code generator
 *
 * Created by Nstd on 2020/6/29 15:40.
 */
class KotlinDataClassCodeBuilder : ICodeBuilder<DataClass> {
    companion object {
        const val CONF_KOTLIN_IS_DATA_CLASS = "code.builder.kotlin.isDataClass"
        const val CONF_KOTLIN_IS_USE_CONSTRUCTOR_PARAMETER = "code.builder.kotlin.isUseConstructorParameter"
        const val CONF_BUILD_FROM_JSON_OBJECT = "code.builder.buildFromJsonObject"
        val DEFAULT = KotlinDataClassCodeBuilder()
    }

    private var isDataClass = true
    private var isUseConstructorParameter: Boolean = true
    private var isBuildFromJsonObject: Boolean = false
    private val baseTypeList = listOf<String>("Int", "String", "Boolean", "Double", "Float", "Long")


    private fun DataClass.genClassTitle(sb: StringBuilder) {
        if (CodeBuilderConfig.instance.getConfig(InternalModifierSupport.CONFIG_KEY, false)) {
            sb.append("internal ")
        }
        if (isDataClass && properties.isNotEmpty()) {
            sb.append("data ")
        }
        sb.append("class ").append(name)
    }

    private fun DataClass.genConstructor(sb: StringBuilder) {
        if (isUseConstructorParameter) {
            sb.append("(").append("\n")
            genJsonFields(sb, getIndent(), true)
            sb.append(")")
        }
    }

    private fun DataClass.genParentClass(sb: StringBuilder) {
        if (parentClassTemplate.isNotBlank()) {
            sb.append(" : ").append(parentClassTemplate)
        }
    }

    private fun DataClass.genBody(sb: StringBuilder) {
        val nestedClasses = referencedClasses.filter { it.modifiable }
        val hasMember = !isUseConstructorParameter && properties.isNotEmpty()
        val isBuildFromJson = isBuildFromJsonObject && properties.isNotEmpty()
        if (!hasMember && !isBuildFromJson && nestedClasses.isEmpty()) return
        val indent = getIndent()
        val bodyBuilder = StringBuilder()
        if (hasMember) genJsonFields(bodyBuilder, indent, false)
        if (isBuildFromJson) genBuildFromJsonObject(bodyBuilder, indent)
        if (nestedClasses.isNotEmpty()) {
            val nestedClassesCode = nestedClasses.map { it.getCode() }.filter { it.isNotBlank() }.joinToString("\n\n")
            bodyBuilder.append(nestedClassesCode.addIndent(indent))
            bodyBuilder.append("\n")
        }
        if (bodyBuilder.isNotBlank()) {
            sb.append(" {").append("\n")
            sb.append(bodyBuilder)
            sb.append("}")
        }
    }

    private fun DataClass.genJsonFields(sb: StringBuilder, indent: String, isAddSeparator: Boolean) {
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

    private fun DataClass.genBuildFromJsonObject(sb: java.lang.StringBuilder, indent: String) {
        sb.newLine()
        sb.append(indent * 1).append("companion object {").newLine()
        sb.append(indent * 2).append("@JvmStatic").newLine()
        sb.append(indent * 2).append("fun buildFromJson(jsonObject: JSONObject?): $name? {").newLine().newLine()
        sb.append(indent * 3).append("jsonObject?.run {").newLine()
        sb.append(indent * 4).append("return $name(").newLine()
        properties.filterNot { excludedProperties.contains(it.name) }.forEachIndexed { index, property ->
            when {
                baseTypeList.contains(property.type.replace("?", "")) -> {
                    sb.append(indent * 5).append("opt${property.type.replace("?", "")}(\"${property.originName}\")")
                }
                property.type.contains("List<") -> {
                    val type = property.type.substring(property.type.indexOf('<') + 1, property.type.indexOf('>'))
                    sb.append(indent * 5).append("Array${property.type.replace("?", "")}().apply {").newLine()
                    sb.append(indent * 6).append("optJSONArray(\"${property.originName}\")?.let {").newLine()
                    sb.append(indent * 7).append("for(i in 0 until it.length()) {").newLine()
                    sb.append(indent * 8).append("this.add($type.buildFromJson(it.getJSONObject(i)))").newLine()
                    sb.append(indent * 7).append("}").newLine()
                    sb.append(indent * 6).append("}").newLine()
                    sb.append(indent * 5).append("}")
                }
                else -> {
                    sb.append(indent * 5).append(
                        "${
                            property.type.replace(
                                "?",
                                ""
                            )
                        }.buildFromJson(optJSONObject(\"${property.originName}\"))"
                    )
                }
            }

            if (index < properties.size - 1) {
                sb.append(",")
            }
            sb.newLine()
        }
        sb.append(indent * 4).append(")").newLine()
        sb.append(indent * 3).append("}").newLine()
        sb.append(indent * 3).append("return null").newLine()
        sb.append(indent * 2).append("}").newLine()
        sb.append(indent * 1).append("}").newLine()
    }

    override fun getCode(clazz: DataClass): String {
        isDataClass = CodeBuilderConfig.instance.getConfig(CONF_KOTLIN_IS_DATA_CLASS, true)
        isUseConstructorParameter =
            CodeBuilderConfig.instance.getConfig(CONF_KOTLIN_IS_USE_CONSTRUCTOR_PARAMETER, isUseConstructorParameter)
        isBuildFromJsonObject = CodeBuilderConfig.instance.getConfig(CONF_BUILD_FROM_JSON_OBJECT, isBuildFromJsonObject)
        clazz.run {
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
                if (excludedProperties.isNotEmpty()) {
                    append(" : ")
                    append(parentClass!!.name)
                    append("(")
                    append(properties.map {
                        it.name to it
                    }.toMap().filter { excludedProperties.contains(it.key) }
                        .map { it.value.inheritanceCode() }.joinToString(", "))
                    append(")")
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
}
