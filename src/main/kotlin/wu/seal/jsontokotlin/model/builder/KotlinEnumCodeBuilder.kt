package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.EnumClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.getIndent
import wu.seal.jsontokotlin.utils.toAnnotationComments
import java.lang.StringBuilder

/**
 * Created by Nstd on 2020/6/30 14:52.
 */
data class KotlinEnumCodeBuilder(
        val clazz: EnumClass
        ): BaseEnumCodeBuilder(
            clazz.name,
            clazz.modifiable,
            clazz.xEnumNames,
            clazz.generic,
            clazz.enum,
            clazz.comments
            ){

    override fun getCode(): String {
        val indent = getIndent()
        return StringBuilder().append(comments.toAnnotationComments())
                .append("enum class $name(val value: ${generic.name}) {\n")
                .append(generateValues().joinToString("\n\n") { "$indent$it" })
                .append("\n}").toString()
    }

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
}