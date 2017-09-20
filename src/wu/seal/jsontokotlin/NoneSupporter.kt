package wu.seal.jsontokotlin

/**
 *
 * Created by Seal.Wu on 2017/9/18.
 */

interface INoneLibSupporter {
    /**
     * create property String block
     */
    fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String


    fun getNoneLibSupporterClassName(rawClassName: String)

}


object NoneSupporter : INoneLibSupporter {

    override fun getNoneLibSupporterClassName(rawClassName: String) {
    }


    override fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val blockBulder = StringBuilder()

        blockBulder.append(PropertyKeyword.get())
        blockBulder.append(" ")
        blockBulder.append(PropertyNameMaker.makePropertyName(rawPropertyName, false))
        blockBulder.append(": ").append(propertyType).append(",")

        return blockBulder.toString()
    }

}