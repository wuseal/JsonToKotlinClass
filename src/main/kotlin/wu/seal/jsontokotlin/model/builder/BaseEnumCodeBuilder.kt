package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Created by Nstd on 2020/6/30 14:49.
 */
abstract class BaseEnumCodeBuilder(
        override val name: String,
        override val modifiable: Boolean,
        val xEnumNames: List<String>?,
        val generic: KotlinClass,
        val enum: List<Any>,
        val comments: String = ""
        ): ICodeBuilder {

    override fun getOnlyCurrentCode(): String {
        return getCode()
    }
}