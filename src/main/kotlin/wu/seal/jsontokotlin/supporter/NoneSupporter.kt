package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.utils.getIndent

/**
 *
 * Created by Seal.Wu on 2017/9/18.
 */

interface INoneLibSupporter {
    /**
     * create property String block
     */
    fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String


    fun getNoneLibSupporterClassName(rawClassName: String):String

}


object NoneSupporter : INoneLibSupporter {


    override fun getNoneLibSupporterClassName(rawClassName: String):String {
        return ""
    }


    override fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val blockBuilder = StringBuilder()

        blockBuilder.append(getIndent())
        blockBuilder.append("val")
        blockBuilder.append(" ")
        blockBuilder.append(rawPropertyName)
        blockBuilder.append(": ").append(propertyType)
        return blockBuilder.toString()
    }

}

