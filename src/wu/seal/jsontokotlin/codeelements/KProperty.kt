package wu.seal.jsontokotlin.codeelements

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.supporter.*
import wu.seal.jsontokotlin.utils.getIndent

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

    private val indent = getIndent()

    override fun getPropertyStringBlock(): String {
        val blockBulder = StringBuilder()

        blockBulder.append(indent)


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

            val stringBuilder = StringBuilder()

            jsonLibSupportPropertyBlockString.split("\n").forEach {

                if (it.isNotEmpty()) {
                    stringBuilder.append(it)
                    stringBuilder.append("\n$indent")
                }
            }
            blockBulder.append(stringBuilder.toString().dropLast(3))

        }


        if (!ConfigManager.isCommentOff && propertyValue.isNotBlank()) {
            blockBulder.append(" //").append(propertyValue)
        }

        return blockBulder.toString()
    }

}
