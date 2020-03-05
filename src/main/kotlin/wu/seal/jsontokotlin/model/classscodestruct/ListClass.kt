package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinClassInterceptor
import wu.seal.jsontokotlin.utils.getIndent

/**
 * ListClass present the class is a class which extends List
 * Created by Seal.Wu on 2019/11/17.
 */
data class ListClass(
        override val name: String,
        override val generic: KotlinClass,
        override val referencedClasses: List<KotlinClass> = listOf(generic),
        override val modifiable: Boolean = true
) : UnModifiableGenericClass() {

    private val indent = getIndent()

    override fun getOnlyCurrentCode(): String {
        return """
            class $name : ArrayList<${generic.name}>()
        """.trimIndent()
    }

    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): ListClass {
        return copy(generic = replaceRule.values.toList()[0], referencedClasses = replaceRule.values.toList())
    }

    override fun rename(newName: String) = copy(name = newName)

    override fun getCode(): String {
        return if (generic.modifiable.not()) {
            getOnlyCurrentCode()
        } else {
            """
            class $name : ArrayList<${generic.name}>(){
${referencedClasses.filter { it.modifiable }.joinToString("\n\n") { it.getCode().prependIndent("            $indent") }}
            }
        """.trimIndent()
        }
    }

    override fun <T : KotlinClass> applyInterceptors(enabledKotlinClassInterceptors: List<IKotlinClassInterceptor<T>>): KotlinClass {
        val newGenerics = generic.applyInterceptors(enabledKotlinClassInterceptors)
        val newImportedClasses = referencedClasses.map { it.applyInterceptors(enabledKotlinClassInterceptors) }
        return copy(generic = newGenerics, referencedClasses = newImportedClasses)
    }
}

