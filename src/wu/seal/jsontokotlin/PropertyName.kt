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
    fun makePropertyName(rawString: String, needTransformToLegalName: Boolean): String


}

object PropertyNameMaker : IPropertyNameMaker {


    private val ilegalPropertyNameList = listOf<String>(
            "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "interface", "is", "null"
            , "object", "package", "return", "super", "this", "throw", "true", "try", "typealias", "val", "var", "when", "while"
    )


    private val ilegalCharactor = listOf<String>(
            "\\+", "\\-", "\\*", "/", "%", "=", "&", "|", "!", "\\[", "\\]", "\\{", "\\}", "\\(", "\\)"
            , ",", ".", ":", "\\?", "\\>", "\\<", "@", ";", "'", "\\`", "\\~", "\\$", "^", "#", "\\", "/"
    )

    @JvmStatic
    fun main(args: Array<String>) {
        println(PropertyNameMaker.ilegalCharactor)
    }

    private val suffix = "X"

    override fun makePropertyName(rawString: String): String {

        return rawString
    }

    override fun makePropertyName(rawString: String, needTransformToLegalName: Boolean): String {

        if (needTransformToLegalName) {

            /**
             * keep character " "
             */
            val pattern = "$ilegalCharactor".replace(" ", "")

            val temp = rawString.replace(Regex(pattern), "").let {

                return@let removeStartNumber(it)

            }

            val lowerCamelCaseName = toLowerCamelCase(temp)

            val legalName = toBeLegalName(lowerCamelCaseName)

            return legalName

        } else {
            return rawString
        }

    }

    private fun toBeLegalName(name: String): String {
        val legalName = if (name in ilegalPropertyNameList) {
            name + suffix
        } else {
            name
        }
        return legalName
    }

    private fun toLowerCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex("[_ ]")).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val camelCaseName = stringBuilder.toString()

        val lowerCamelCaseName = camelCaseName.substring(0, 1).toLowerCase().plus(camelCaseName.substring(1))

        return lowerCamelCaseName

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