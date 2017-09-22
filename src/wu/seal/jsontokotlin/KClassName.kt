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
                !@3214 12#$%n^&*(-a)_+-=m12335_e43{}|[]\\;':1",./<>?/*-+`
                """
    println("orginal name is |$name1|")
    println("Name1 is :   |${KClassName.getName(name1)}|")
}

object KClassName : KName(), IKClassName {

    override fun getLegalClassName(rawClassName: String): String {

        return getUpperCamelCaseLegalName(rawClassName)
    }

    private fun getUpperCamelCaseLegalName(rawClassName: String): String {
        /**
         * keep " " character
         */
        val pattern = "$illegalCharacter".replace(Regex(nameSeparator.toString()), "")

        val temp = rawClassName.replace(Regex(pattern), "").let {

            return@let removeStartNumberAndIllegalCharacter(it)

        }

        val upperCamelCase = toUpperCamelCase(temp)

        val legalName = toBeLegalName(upperCamelCase)

        return legalName
    }


    override fun getName(rawName: String): String {

        return getLegalClassName(rawName)
    }


    /**
     * this function can remove the rest white space
     */
    private fun toUpperCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex(nameSeparator.toString())).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val upperCamelCaseName = stringBuilder.toString()

        return upperCamelCaseName

    }


}


