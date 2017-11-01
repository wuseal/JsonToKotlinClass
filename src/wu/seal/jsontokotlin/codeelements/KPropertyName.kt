package wu.seal.jsontokotlin.codeelements

/**
 * make name to be camel case
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
    println("Name1 is :   |${wu.seal.jsontokotlin.codeelements.KPropertyName.getName(name1)}|")

}

object KPropertyName : KName(), wu.seal.jsontokotlin.codeelements.IPropertyNameMaker {


    override fun getName(rawName: String): String {

        return wu.seal.jsontokotlin.codeelements.KPropertyName.makePropertyName(rawName, true)
    }

    override fun makePropertyName(rawString: String): String {

        return rawString
    }

    override fun makePropertyName(rawString: String, needTransformToLegalName: Boolean): String {

        if (needTransformToLegalName) {

            return wu.seal.jsontokotlin.codeelements.KPropertyName.makeCamelCaseLegalName(rawString)

        } else {
            return rawString
        }

    }

    private fun makeCamelCaseLegalName(rawString: String): String {
        /**
         * keep nameSeparator character
         */
        val pattern = "${wu.seal.jsontokotlin.codeelements.KPropertyName.illegalCharacter}".replace(Regex(wu.seal.jsontokotlin.codeelements.KPropertyName.nameSeparator.toString()), "")

        val temp = rawString.replace(Regex(pattern), "").let {

            return@let wu.seal.jsontokotlin.codeelements.KPropertyName.removeStartNumberAndIllegalCharacter(it)

        }

        val lowerCamelCaseName = wu.seal.jsontokotlin.codeelements.KPropertyName.toLowerCamelCase(temp)

        val legalName = wu.seal.jsontokotlin.codeelements.KPropertyName.toBeLegalName(lowerCamelCaseName)

        return legalName
    }


    /**
     * this function can remove the rest white space
     */
    private fun toLowerCamelCase(temp: String): String {

        val stringBuilder = StringBuilder()

        temp.split(Regex(wu.seal.jsontokotlin.codeelements.KPropertyName.nameSeparator.toString())).forEach {
            if (it.isNotBlank()) {
                stringBuilder.append(it.substring(0, 1).toUpperCase().plus(it.substring(1)))
            }
        }

        val camelCaseName = stringBuilder.toString()

        val lowerCamelCaseName = camelCaseName.substring(0, 1).toLowerCase().plus(camelCaseName.substring(1))

        return lowerCamelCaseName

    }



}