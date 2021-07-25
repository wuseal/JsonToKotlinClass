package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.utils.getIndent

/**
 * Code generator interface
 *
 * Created by Nstd on 2020/6/29 15:27.
 */
interface ICodeBuilder<C : KotlinClass> {

    val indent: String
        get() = getIndent()

    /**
     * get the code (include referenced classes) string for writing into file or printing out
     */
    fun getCode(clazz: C): String

    /**
     * only the current class code not include the referenced class for writing into file or printing out
     */
    fun getOnlyCurrentCode(clazz: C): String

    companion object {
        val EMPTY = object : ICodeBuilder<KotlinClass> {
            override fun getCode(clazz: KotlinClass): String {
                return ""
            }

            override fun getOnlyCurrentCode(clazz: KotlinClass): String {
                return ""
            }
        }
    }
}