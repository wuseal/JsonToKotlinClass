package wu.seal.jsontokotlin

/**
 * Transform to legal Class name
 * Created by Seal on 2017/9/18.
 */

interface IKClassName {
    fun getLegalClassName(rawClassName: String): String

}

fun main(args: Array<String>) {
    val name1 = """
                !@3214 12#$%n^&*(a)_+-=m12335e43{}|[]\\;':1",./<>?/*-+`
                """
    println("orginal name is |$name1|")
    println("Name1 is :   |${KClassName.getName(name1)}|")
}

object KClassName : KName(), IKClassName {


    private val suffix = "X"


    override fun getLegalClassName(rawClassName: String): String {

        /**
         * keep " " character
         */
        val pattern = "$illegalCharacter".replace(" ", "")

        val temp = rawClassName.replace(Regex(pattern), "").let {

            return@let removeStartNumberAndWhiteSpace(it)

        }

        val upperCamelCase = toUpperCamelCase(temp)

        val legalName = toBeLegalName(upperCamelCase)

        return legalName
    }


    override fun getName(rawName: String): String {

        return getLegalClassName(rawName)
    }


    private fun toBeLegalName(name: String): String {
        val legalName = if (name in illegalNameList) {
            name + suffix
        } else {
            name
        }
        return legalName
    }

    /**
     * this function can remove the rest white space
     */
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
    private fun removeStartNumberAndWhiteSpace(it: String): String {
        return if (it.trim().indexOfFirst {
            return@indexOfFirst it in '0'..'9'
        } == 0) {
            it.trim().replaceFirst(Regex("[ \\d]{1,}"), "")
        } else {
            it
        }
    }
}


