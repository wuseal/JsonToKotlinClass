package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.model.builder.*
import wu.seal.jsontokotlin.utils.*

data class DataClass(
        val annotations: List<Annotation> = listOf(),
        override val name: String,
        val properties: List<Property> = listOf(),
        val parentClassTemplate: String = "",
        override val modifiable: Boolean = true,
        val comments: String = "",
        val fromJsonSchema: Boolean = false,
        val lang: String = "kotlin"
) : ModifiableKotlinClass, NoGenericKotlinClass {

    override val hasGeneric: Boolean = false

    private val codeBuilder: ICodeBuilder
        get() = CodeBuilderFactory.get("class", lang, this)

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
                    is ModifiableKotlinClass -> replaceRule[property.typeObject]
                            ?: error("Modifiable Kotlin Class Must have a replacement")
                    else -> it
                }
                LogUtil.i("replace type: ${property.type} to ${newTypObj.name}")
                val typeSuffix = if (property.type.endsWith("?")) "?" else ""
                return@let property.copy(type = "${newTypObj.name}$typeSuffix", typeObject = newTypObj)
            }
        }

        return copy(properties = newProperties)
    }

    override fun getCode(): String {
        return codeBuilder.getCode()
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
        return codeBuilder.getOnlyCurrentCode()
    }
}
