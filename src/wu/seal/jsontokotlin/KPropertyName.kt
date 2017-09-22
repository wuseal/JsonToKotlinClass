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

fun main(args: Array<String>) {
    val name1 = """
                !@#$ -_32322 3 32%N^&*(-a)_+-=m123-35 e43{}|[]\\;':1",./<>?/*-+`
                """

    println("orginal name is |$name1|")
    println("Name1 is :   |${KPropertyName.getName(name1)}|")

}

object KPropertyName : KName(), IPropertyNameMaker {


    @JvmStatic
    fun main(args: Array<String>) {
        println(KPropertyName.illegalCharacter)
    }

    private val suffix = "X"


    override fun getName(rawName: String): String {

        return makePropertyName(rawName, true)
    }

    override fun makePropertyName(rawString: String): String {

        return rawString
    }

    override fun makePropertyName(rawString: String, needTransformToLegalName: Boolean): String {

        if (needTransformToLegalName) {

            /**
             * keep character " "
             */
            val pattern = "$illegalCharacter".replace(Regex(nameSeparator.toString()), "")

            val temp = rawString.replace(Regex(pattern), "").let {

                return@let removeStartNumberAndIllegalCharacter(it)

            }

            val lowerCamelCaseName = toLowerCamelCase(temp)

            val legalName = toBeLegalName(lowerCamelCaseName)

            return legalName

        } else {
            return rawString
        }

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
    private fun toLowerCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex(nameSeparator.toString())).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val camelCaseName = stringBuilder.toString()

        val lowerCamelCaseName = camelCaseName.substring(0, 1).toLowerCase().plus(camelCaseName.substring(1))

        return lowerCamelCaseName

    }

    /**
     * remove the start number or whiteSpace characters in this string
     */
    private fun removeStartNumberAndIllegalCharacter(it: String): String {

        return if (it.replace(Regex(illegalCharacter.toString()), "").indexOfFirst {
            return@indexOfFirst it in '0'..'9'
        } == 0) {

            val numberAndIllegalCharacters = listOf<String>(*illegalCharacter.toTypedArray(), "\\d")

            it.trim().replaceFirst(Regex("${numberAndIllegalCharacters.toString().trim()}{1,}"), "")
        } else {
            it
        }
    }

}