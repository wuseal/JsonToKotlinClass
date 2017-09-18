package wu.seal.jsontokotlin

/**
 * Created by Sealwu on 2017/9/18.
 */

interface IPropertyNameMaker {

    /**
     * make legal property name from a input raw string
     */
    fun makePropertyName(rawString: String): String


    /**
     * make legal property name from a input raw string
     */
    fun makePropertyName(rawString: String, needTransformtToIlegalNameMaker: Boolean): String


}


object PropertyNameMaker : IPropertyNameMaker {


    private val ilegalPropertyNameList = listOf<String>(
            "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "interface", "is", "null"
            , "object", "package", "return", "super", "this", "throw", "true", "try", "typealias", "val", "var", "when", "while"
    )


    private val ilegalCharactor = listOf<String>(
            "+", "-", "*", "/", "%", "=", "&", "|", "!", "[", "]", "{", "}", "(", ")"
            , ",", ".", ":", "?", ">", "<", "@", ";", "`", "$", "^", "#", "\\", "/", " "
    )

    private val suffix = "X"

    override fun makePropertyName(rawString: String): String {

        return rawString
    }

    override fun makePropertyName(rawString: String, needTransformtToIlegalNameMaker: Boolean): String {

        val pattern = "$ilegalCharactor"

        val temp = rawString.replace(Regex(pattern), "")

        return if (temp in ilegalPropertyNameList) {
            return temp + suffix
        } else {
            temp
        }
    }

}