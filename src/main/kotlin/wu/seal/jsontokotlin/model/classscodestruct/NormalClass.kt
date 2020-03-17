package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.utils.LogUtil
import wu.seal.jsontokotlin.utils.getCommentCode
import wu.seal.jsontokotlin.utils.getIndent
import java.lang.IllegalStateException

/**
 * Created by Seal.Wu on 2020-03-05
 * Normal general class code struct
 * diff with data class is that there no 'data' modifier
 */
data class NormalClass(
        val annotations: List<Annotation> = listOf(),
        override val name: String,
        val properties: List<Property> = listOf(),
        val parentClassTemplate: String = "",
        override val modifiable: Boolean = true
) : ModifiableKotlinClass, NoGenericKotlinClass {

    override val hasGeneric: Boolean = false

    override val referencedClasses: List<KotlinClass>
        get() {
            return properties.flatMap { property ->
                mutableListOf(property.typeObject).apply {
                    addAll(property.typeObject.getAllGenericsRecursively())
                }
            }
        }

    override fun rename(newName: String): KotlinClass = copy(name = newName)

    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): KotlinClass {
        val propertiesReferencedModifiableKotlinClass = properties.flatMap {
            if (it.typeObject is GenericKotlinClass) {
                it.typeObject.getAllGenericsRecursively().toMutableList().also { list -> list.add(it.typeObject) }
            } else {
                listOf(it.typeObject)
            }
        }.filter { it.modifiable }
        if (propertiesReferencedModifiableKotlinClass.size != replaceRule.size) {
            throw IllegalStateException("properties used kotlin classes size should be equal referenced classes size!")
        }
        if (!replaceRule.all { it.key.modifiable }) {
            throw IllegalStateException("to be replaced referenced class should be modifiable!")
        }
        val newProperties = properties.map { property ->
            property.typeObject.let {
                val newTypObj = when (it) {
                    is GenericKotlinClass -> property.typeObject.replaceReferencedClasses(replaceRule)
                    is ModifiableKotlinClass -> replaceRule[property.typeObject] ?: error("Modifiable Kotlin Class Must have a replacement")
                    else -> it
                }
                LogUtil.i("replace type: ${property.type} to ${newTypObj.name}")
                return@let property.copy(type = newTypObj.name, typeObject = newTypObj)
            }
        }

        return copy(properties = newProperties)
    }

    override fun getCode(): String {
        val indent = getIndent()
        return buildString {
            if (annotations.isNotEmpty()) {
                val annotationsCode = annotations.joinToString("\n") { it.getAnnotationString() }
                if (annotationsCode.isNotBlank()) {
                    append(annotationsCode).append("\n")
                }
            }
            if (properties.isEmpty()) {
                append("class ").append(name).append("(").append("\n")
            } else {
                append("class ").append(name).append("(").append("\n")
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
            val nestedClasses = referencedClasses.filter { it.modifiable }
            if (nestedClasses.isNotEmpty()) {
                append(" {")
                append("\n")
                val nestedClassesCode = nestedClasses.joinToString("\n\n") { it.getCode() }
                append(nestedClassesCode.lines().joinToString("\n") { if (it.isNotBlank()) "$indent$it" else it })
                append("\n")
                append("}")
            }
        }
    }


    override fun <T : KotlinClass> applyInterceptors(enabledKotlinClassInterceptors: List<IKotlinClassInterceptor<T>>): KotlinClass {
        val newProperties = mutableListOf<Property>()
        properties.forEach {
            newProperties.add(it.copy(typeObject = it.typeObject.applyInterceptors(enabledKotlinClassInterceptors)))
        }
        var newKotlinDataClass: KotlinClass = copy(properties = newProperties)
        enabledKotlinClassInterceptors.forEach {
            newKotlinDataClass = it.intercept(newKotlinDataClass)
        }
        return newKotlinDataClass
    }

    override fun getOnlyCurrentCode(): String {
        val newProperties = properties.map { it.copy(typeObject = KotlinClass.ANY) }
        return copy(properties = newProperties).getCode()
    }
}
