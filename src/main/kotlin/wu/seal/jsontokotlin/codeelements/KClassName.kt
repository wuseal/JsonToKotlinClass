package wu.seal.jsontokotlin.codeelements


/**
 * Transform to legal Class name
 * Created by Seal on 2017/9/18.
 */

interface IKClassName {
    fun getLegalClassName(rawClassName: String): String

}

object KClassName : KName(), IKClassName {

    override fun getLegalClassName(rawClassName: String): String {

        val upperCamelCaseLegalName = getUpperCamelCaseLegalName(rawClassName)
        if (upperCamelCaseLegalName.isNotEmpty()) {

            return upperCamelCaseLegalName
        } else {
            return getUpperCamelCaseLegalName("X-"+rawClassName)
        }
    }

    private fun getUpperCamelCaseLegalName(rawClassName: String): String {
        /**
         * keep " " character
         */
        val pattern = "$illegalCharacter".replace(Regex(nameSeparator.toString()), "")

        val temp = rawClassName.replace(Regex(pattern), "").let {

            return@let removeStartNumberAndIllegalCharacter(it)

        }

        val upperCamelCase =toUpperCamelCase(temp)

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


