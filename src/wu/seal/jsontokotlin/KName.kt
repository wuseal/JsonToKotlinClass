package wu.seal.jsontokotlin

/**
 * Name class
 * Created by Seal.Wu on 2017/9/21.
 */


interface IKName {

    fun getName(rawName: String): String

}

abstract class KName : IKName {

    protected val illegalNameList = listOf<String>(
            "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "interface", "is", "null"
            , "object", "package", "return", "super", "this", "throw", "true", "try", "typealias", "val", "var", "when", "while"
    )


    protected val illegalCharacter = listOf<String>(
            "\\+", "\\-", "\\*", "/", "%", "=", "&", "|", "!", "\\[", "\\]", "\\{", "\\}", "\\(", "\\)", "\\\\", "\""
            , ",", ".", ":", "\\?", "\\>", "\\<", "@", ";", "'", "\\`", "\\~", "\\$", "^", "#", "\\", "/", " ", "\t", "\n"
    )

}


fun main(args: Array<String>) {
    println("\\")
}