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
    println("getNoneLibSupporterProperty:\n ${NoneSupporter.getNoneLibSupporterProperty("seal is **() good_man ", "Boy")}")
}


object NoneSupporter : INoneLibSupporter {

    override fun getNoneLibSupporterClassName(rawClassName: String) {
    }


    override fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val blockBuilder = StringBuilder()

        blockBuilder.append(PropertyKeyword.get())
        blockBuilder.append(" ")
        blockBuilder.append(rawPropertyName)
        blockBuilder.append(": ").append(propertyType)
        if (ConfigManager.initWithDefaultValue) {
            blockBuilder.append(" = ").append(getDefaultValue(propertyType))
        }
        blockBuilder.append(",")

        return blockBuilder.toString()
    }

}

