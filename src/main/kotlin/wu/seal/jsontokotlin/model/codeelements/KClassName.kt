package wu.seal.jsontokotlin.model.codeelements


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
        return if (upperCamelCaseLegalName.isNotEmpty()) {
            upperCamelCaseLegalName
        } else {
            getUpperCamelCaseLegalName("X-$rawClassName")
        }
    }

    private fun getUpperCamelCaseLegalName(rawClassName: String): String {
        /**
         * keep " " character
         */
        val pattern = illegalCharacter.toMutableList().apply { removeAll(nameSeparator) }.toRegex()

        val temp = rawClassName.replace(pattern, "").let {

            return@let removeStartNumberAndIllegalCharacter(it)

        }

        val upperCamelCase =toUpperCamelCase(temp)

        return toBeLegalName(upperCamelCase)
    }


    override fun getName(rawName: String): String {

        return getLegalClassName(rawName)
    }


    /**
     * this function can remove the rest white space
     */
    private fun toUpperCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(nameSeparator.toRegex()).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        return stringBuilder.toString()

    }


}


