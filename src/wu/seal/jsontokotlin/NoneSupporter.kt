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

fun main(args: Array<String>) {

    isTestModel = true
    println("getNoneLibSupporterProperty:\n ${NoneSupporter.getNoneLibSupporterProperty("seal is **() good_man ","Boy")}")
}


object NoneSupporter : INoneLibSupporter {

    override fun getNoneLibSupporterClassName(rawClassName: String) {
    }


    override fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val blockBulder = StringBuilder()

        blockBulder.append(PropertyKeyword.get())
        blockBulder.append(" ")
        blockBulder.append(KPropertyName.getName(rawPropertyName))
        blockBulder.append(": ").append(propertyType).append(",")

        return blockBulder.toString()
    }

}

