package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.ListClass
import wu.seal.jsontokotlin.utils.getIndent

/**
 * Created by Nstd on 2020/6/30 15:59.
 */
data class KotlinListCodeBuilder(
        val clazz: ListClass
        ): BaseListCodeBuilder(
                clazz.name,
                clazz.modifiable,
                clazz.generic,
                clazz.referencedClasses
                ) {

    private val indent = getIndent()

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

    override fun getOnlyCurrentCode(): String {
        return """
            class $name : ArrayList<${generic.name}>()
        """.trimIndent()
    }
}