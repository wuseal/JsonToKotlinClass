package wu.seal.jsontokotlin.model.classscodestruct

import wu.seal.jsontokotlin.model.builder.CodeBuilderFactory
import wu.seal.jsontokotlin.model.builder.ICodeBuilder

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
        override val modifiable: Boolean = true,
        val lang: String = "kotlin"
) : ModifiableKotlinClass, NoGenericKotlinClass {

    private val codeBuilder: ICodeBuilder
        get() = CodeBuilderFactory.get("enum", lang, this)

    override fun getOnlyCurrentCode(): String {
        return codeBuilder.getOnlyCurrentCode()
    }

    override fun rename(newName: String): KotlinClass = copy(name = newName)

    override fun getCode(): String {
        return codeBuilder.getCode()
    }

    override fun replaceReferencedClasses(replaceRule: Map<KotlinClass, KotlinClass>): EnumClass {
        return if (replaceRule.isEmpty()) this else copy(generic = replaceRule.values.toList()[0], referencedClasses = replaceRule.values.toList())
    }
}