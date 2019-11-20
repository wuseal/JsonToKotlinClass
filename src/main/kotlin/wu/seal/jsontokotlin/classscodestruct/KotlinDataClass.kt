package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.LogUtil
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.getIndent
import java.lang.IllegalStateException

data class KotlinDataClass(
        val id: Int = -1, // -1 represent the default unknown id
        val annotations: List<Annotation> = listOf(),
        override val name: String,
        val properties: List<Property> = listOf(),
        val parentClassTemplate: String = "",
        override val modifiable: Boolean = true
) : KotlinClass {

    override val referencedClasses: List<KotlinClass>
        get() {
            return properties.mapNotNull { it.typeObject }
        }

    override fun rename(newName: String): KotlinClass = copy(name = newName)

    override fun replaceReferencedClasses(referencedClasses: List<KotlinClass>): KotlinClass {
        val propertiesReferencedKotlinClass = properties.filter { it.typeObject != null }
        if (propertiesReferencedKotlinClass.size != referencedClasses.size) {
            throw IllegalStateException("properties used kotlin classes size should be equal referenced classes size!")
        }
        var replaceRefClassIndex = 0
        val newProperties = properties.map { property ->
            property.typeObject?.let {
                val newTypObj = referencedClasses[replaceRefClassIndex++] as KotlinDataClass
                val newType = property.type.replace(property.typeObject.name,newTypObj.name)
                LogUtil.i("replace type: ${property.type} to $newType")
                return@let property.copy(type = newType, typeObject = newTypObj)
            } ?: property
        }

        return copy(properties = newProperties)
    }

    override fun getCode(): String {
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
                val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode() }
                append(nestedClassesCode.lines().joinToString("\n") { if (it.isNotBlank()) "    $it" else it })
                append("\n")
                append("}")
            }
        }
        return code
    }

    override fun applyInterceptors(enabledKotlinDataClassInterceptors: List<IKotlinDataClassInterceptor>): KotlinDataClass {
        val newProperties = mutableListOf<Property>()
        properties.forEach {
            if (it.typeObject != null) {
                newProperties.add(it.copy(typeObject = it.typeObject.applyInterceptors(enabledKotlinDataClassInterceptors)))
            } else {
                newProperties.add(it)
            }
        }
        var newKotlinDataClass = copy(properties = newProperties)
        enabledKotlinDataClassInterceptors.forEach {
            newKotlinDataClass = it.intercept(newKotlinDataClass)
        }
        return newKotlinDataClass
    }

    override fun getOnlyCurrentCode(): String {
        val newProperties = properties.map { it.copy(typeObject = null) }
        return copy(properties = newProperties).getCode()
    }
}
