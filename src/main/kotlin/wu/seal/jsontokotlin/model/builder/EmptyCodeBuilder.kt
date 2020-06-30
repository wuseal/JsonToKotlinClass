package wu.seal.jsontokotlin.model.builder

/**
 * Created by Nstd on 2020/6/30 18:56.
 */
class EmptyCodeBuilder(
        override val name: String = "",
        override val modifiable: Boolean = false
        ) : ICodeBuilder {

    override fun getCode(): String {
        return "//TODO language is no specific"
    }

    override fun getOnlyCurrentCode(): String {
        return "//TODO language is no specific"
    }
}