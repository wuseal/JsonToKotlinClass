package wu.seal.jsontokotlin.classscodestruct

import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.getIndent

/**
 * ListClass present the class is a class which extends List
 * Created by Seal.Wu on 2019/11/17.
 */
data class ListClass(
        override val name: String,
        val generics: KotlinClass,
        override val referencedClasses: List<KotlinClass> = listOf(generics),
        override val modifiable: Boolean = true
) : KotlinClass {

    private val indent = getIndent()

    override fun getOnlyCurrentCode(): String {
        return """
            class $name : ArrayList<${generics.name}>()
        """.trimIndent()
    }

    override fun replaceReferencedClasses(referencedClasses: List<KotlinClass>): ListClass {
        return copy(generics = referencedClasses[0], referencedClasses = referencedClasses)
    }

    override fun rename(newName: String) = copy(name = newName)

    override fun getCode(): String {
        return if (generics.modifiable.not()) {
            getOnlyCurrentCode()
        } else {
            """
            class $name : ArrayList<${generics.name}>(){
${referencedClasses.filter { it.modifiable }.joinToString("\n\n") { it.getCode().prependIndent("            $indent") }}
            }
        """.trimIndent()
        }
    }

    override fun applyInterceptors(enabledKotlinDataClassInterceptors: List<IKotlinDataClassInterceptor>): KotlinClass {
        val newGenerics = generics.applyInterceptors(enabledKotlinDataClassInterceptors)
        val newImportedClasses = referencedClasses.map { it.applyInterceptors(enabledKotlinDataClassInterceptors) }
        return copy(generics = newGenerics, referencedClasses = newImportedClasses)
    }
}

