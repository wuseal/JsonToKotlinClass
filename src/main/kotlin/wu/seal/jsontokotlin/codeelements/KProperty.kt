package wu.seal.jsontokotlin.codeelements

import wu.seal.jsontokotlin.supporter.NoneSupporter

/**
 *
 * Created by Seal.Wu on 2017/9/18.
 */

interface IProperty {
    /**
     *
     */
    fun getPropertyStringBlock(): String

    fun getPropertyComment(): String

}

class KProperty(rawPropertyName: String, private val propertyType: String, private val propertyValue: String) : IProperty {

    private val noLinebreakPropertyName = rawPropertyName.replace("\n","\\n").take(255)

    override fun getPropertyStringBlock(): String {

        val blockBuilder = StringBuilder()

        blockBuilder.append(NoneSupporter.getNoneLibSupporterProperty(noLinebreakPropertyName, propertyType))

        return blockBuilder.toString()
    }

    override fun getPropertyComment(): String = propertyValue
}
