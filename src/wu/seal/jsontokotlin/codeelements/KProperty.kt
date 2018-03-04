package wu.seal.jsontokotlin.codeelements

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.supporter.*

/**
 *
 * Created by Seal.Wu on 2017/9/18.
 */

interface IProperty {
    /**
     *
     */
    fun getPropertyStringBlock(): String

}

class KProperty(private val rawPropertyName: String, private val propertyType: String, private val propertyValue: String) : IProperty {


    override fun getPropertyStringBlock(): String {
        val blockBulder = StringBuilder()

        blockBulder.append("\t\t")


        if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {

            blockBulder.append(NoneSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.NoneWithCamelCase) {

            blockBulder.append(NoneWithCamelCaseSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {

            blockBulder.append(GsonSupporter.getGsonSupporterProperty(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Jackson) {

            blockBulder.append(JacksonSupporter.getJacksonSupporterProperty(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.FastJson) {

            blockBulder.append(FastjsonSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.MoShi) {

            blockBulder.append(MoShiSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare) {

            blockBulder.append(LoganSquareSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom) {

            val jsonLibSupportPropertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType)
            jsonLibSupportPropertyBlockString.split("\n").forEach {
                if (it.isNotEmpty()) {
                    blockBulder.append(it)
                    blockBulder.append("\n\t\t")
                }
            }
        }


        if (!ConfigManager.isCommentOff && propertyValue.isNotBlank()) {
            blockBulder.append(" //").append(propertyValue)
        }

        return blockBulder.toString()
    }

}
