package wu.seal.jsontokotlin.codeelements

/**
 * Name class
 * Created by Seal.Wu on 2017/9/21.
 */


interface IKName {

    fun getName(rawName: String): String

}

abstract class KName : IKName {

    private val suffix = "X"

    protected val illegalNameList = listOf<String>(
            "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "interface", "is", "null"
            , "object", "package", "return", "super", "this", "throw", "true", "try", "typealias", "val", "var", "when", "while"
    )


    protected val illegalCharacter = listOf<String>(
            "\\+", "\\-", "\\*", "/", "%", "=", "&", "|", "!", "\\[", "\\]", "\\{", "\\}", "\\(", "\\)", "\\\\", "\"", "_"
            , ",", ".", ":", "\\?", "\\>", "\\<", "@", ";", "'", "\\`", "\\~", "\\$", "^", "#", "\\", "/", " ", "\t", "\n"
    )


    protected val nameSeparator = listOf<String>(" ", "_", "-")


    /**
     * remove the start number or whiteSpace characters in this string
     */
    protected fun removeStartNumberAndIllegalCharacter(it: String): String {

        return if (it.replace(Regex(illegalCharacter.toString()), "").indexOfFirst {
            return@indexOfFirst it in '0'..'9'
        } == 0) {

            val numberAndIllegalCharacters = listOf<String>(*illegalCharacter.toTypedArray(), "\\d")

            it.trim().replaceFirst(Regex("${numberAndIllegalCharacters.toString().trim()}{1,}"), "")
        } else {
            it
        }
    }

    protected fun toBeLegalName(name: String): String {
        val tempName = name.replace(illegalCharacter.toString(), "")

        val legalName = if (tempName in illegalNameList) {
            tempName + suffix
        } else {
            tempName
        }
        return legalName
    }

}


fun main(args: Array<String>) {
    println("\\")
}