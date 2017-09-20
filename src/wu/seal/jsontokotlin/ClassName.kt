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
            , ",", ".", ":", "\\?", "\\>", "\\<", "@", ";", "'", "\\`", "\\~", "\\$", "^", "#", "\\", "/"
    )

    private val suffix = "X"


    override fun getLegalClassName(rawClassName: String): String {

        /**
         * keep " " character
         */
        val pattern = "$ilegalCharactor".replace(" ", "")

        val temp = rawClassName.replace(Regex(pattern), "").let {

            return@let removeStartNumber(it)

        }

        val upperCamelCase = toUpperCamelCase(temp)

        val legalName = toBeLegalName(upperCamelCase)

        return legalName
    }

    private fun toBeLegalName(name: String): String {
        val legalName = if (name in ilegalClassNameList) {
            name + suffix
        } else {
            name
        }
        return legalName
    }

    private fun toUpperCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex("[_ ]")).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val upperCamelCaseName = stringBuilder.toString()

        return upperCamelCaseName

    }

    /**
     * remove the start number characters in this string
     */
    private fun removeStartNumber(it: String): String {
        return if (it.indexOfFirst {
            return@indexOfFirst it in '0'..'9'
        } == 0) {
            it.replaceFirst(Regex("\\d{1,}"), "")
        } else {
            it
        }
    }
}