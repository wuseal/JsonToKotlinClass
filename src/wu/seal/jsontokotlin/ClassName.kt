package wu.seal.jsontokotlin

/**
 * Transform to legal Class name
 * Created by Seal on 2017/9/18.
 */

interface IKClassName {
    fun getLegalClassName(rawClassName: String): String

}

object KClassName : IKClassName {

    private val ilegalClassNameList = listOf<String>(
            "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "interface", "is", "null"
            , "object", "package", "return", "super", "this", "throw", "true", "try", "typealias", "val", "var", "when", "while"
    )


    private val ilegalCharactor = listOf<String>(
            "\\+", "\\-", "\\*", "/", "%", "=", "&", "|", "!", "\\[", "\\]", "\\{", "\\}", "\\(", "\\)"
            , ",", ".", ":", "\\?", "\\>", "\\<", "@", ";","'", "\\`","\\~" ,"\\$", "^", "#", "\\", "/", " "
    )

    private val suffix = "X"


    override fun getLegalClassName(rawClassName: String): String {

        val pattern = "${ilegalCharactor}"

        val temp = rawClassName.replace(Regex(pattern), "")

        return if (temp in ilegalClassNameList) {
            return temp + suffix
        } else {
            temp
        }
    }
}