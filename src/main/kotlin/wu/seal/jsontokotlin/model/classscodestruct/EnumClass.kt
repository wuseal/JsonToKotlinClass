package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.utils.getIndent
import wu.seal.jsontokotlin.utils.toAnnotationComments
import java.lang.StringBuilder

/**
 * Created by ted on 2020/3/14 18:14.
 */
data class EnumClass(
        override val name: String,
        val xEnumNames: List<String>?,
        override val generic: KotlinClass,
        override val referencedClasses: List<KotlinClass> = listOf(generic),
        val enum: List<Any>,
        val comments: String = "",
        override val modifiable: Boolean = true
) : ModifiableKotlinClass, NoGenericKotlinClass {

    override fun getOnlyCurrentCode(): String {
        val indent = getIndent()
        return StringBuilder().append(comments.toAnnotationComments())
                .append("enum class $name(val value: ${generic.name}) {\n")
                .append(generateValues().joinToString("\n\n") { "$indent$it" })
                .append("\n}").toString()
    }

    override fun rename(newName: String): KotlinClass = copy(name = newName)

    private fun generateValues(): List<String> {
        val list = mutableListOf<String>()
        for (i in enum.indices) {
            val constantValue: Any = if (generic == KotlinClass.INT) (enum[i] as Double).toInt()
            else enum[i].toString()
            val constantName = xEnumNames?.get(i)
                    ?: if (constantValue is Int) "_$constantValue" else constantValue.toString()
            val finalValue = "${constantName}(${constantValue})" + if (i != enum.size - 1) "," else ";"
            list.add(finalValue)
        }
        return list
    }

    override fun getCode(): String {
        return getOnlyCurrentCode()
    }

    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): EnumClass {
        return if (replaceRule.isEmpty()) this else copy(generic = replaceRule.values.toList()[0], referencedClasses = replaceRule.values.toList())
    }
}