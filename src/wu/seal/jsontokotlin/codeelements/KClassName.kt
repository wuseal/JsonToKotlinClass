package wu.seal.jsontokotlin.codeelements

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
    println("Name1 is :   |${wu.seal.jsontokotlin.codeelements.KClassName.getName(name1)}|")
}

object KClassName : KName(), wu.seal.jsontokotlin.codeelements.IKClassName {

    override fun getLegalClassName(rawClassName: String): String {

        return wu.seal.jsontokotlin.codeelements.KClassName.getUpperCamelCaseLegalName(rawClassName)
    }

    private fun getUpperCamelCaseLegalName(rawClassName: String): String {
        /**
         * keep " " character
         */
        val pattern = "${wu.seal.jsontokotlin.codeelements.KClassName.illegalCharacter}".replace(Regex(wu.seal.jsontokotlin.codeelements.KClassName.nameSeparator.toString()), "")

        val temp = rawClassName.replace(Regex(pattern), "").let {

            return@let wu.seal.jsontokotlin.codeelements.KClassName.removeStartNumberAndIllegalCharacter(it)

        }

        val upperCamelCase = wu.seal.jsontokotlin.codeelements.KClassName.toUpperCamelCase(temp)

        val legalName = wu.seal.jsontokotlin.codeelements.KClassName.toBeLegalName(upperCamelCase)

        return legalName
    }


    override fun getName(rawName: String): String {

        return wu.seal.jsontokotlin.codeelements.KClassName.getLegalClassName(rawName)
    }


    /**
     * this function can remove the rest white space
     */
    private fun toUpperCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex(wu.seal.jsontokotlin.codeelements.KClassName.nameSeparator.toString())).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val upperCamelCaseName = stringBuilder.toString()

        return upperCamelCaseName

    }


}


