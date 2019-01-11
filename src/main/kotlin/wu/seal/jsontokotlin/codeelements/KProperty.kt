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

    fun getPropertyComment(): String

}

class KProperty(private val rawPropertyName: String, private val propertyType: String, private val propertyValue: String) : IProperty {

    override fun getPropertyStringBlock(): String {
        val blockBuilder = StringBuilder()


        when (ConfigManager.targetJsonConverterLib) {
            TargetJsonConverter.None -> blockBuilder.append(NoneSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))
            TargetJsonConverter.NoneWithCamelCase -> blockBuilder.append(NoneWithCamelCaseSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))
            TargetJsonConverter.Gson -> blockBuilder.append(GsonSupporter.getGsonSupporterProperty(rawPropertyName, propertyType))
            TargetJsonConverter.Jackson -> blockBuilder.append(JacksonSupporter.getJacksonSupporterProperty(rawPropertyName, propertyType))
            TargetJsonConverter.FastJson -> blockBuilder.append(FastjsonSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))
            TargetJsonConverter.MoShi -> blockBuilder.append(MoShiSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))
            TargetJsonConverter.LoganSquare -> blockBuilder.append(LoganSquareSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType))
            TargetJsonConverter.Custom -> {

                val jsonLibSupportPropertyBlockString = CustomJsonLibSupporter.getJsonLibSupportPropertyBlockString(rawPropertyName, propertyType)

                blockBuilder.append(jsonLibSupportPropertyBlockString)

            }
            else-> blockBuilder.append(NoneSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))
        }

        return blockBuilder.toString()
    }

    override fun getPropertyComment(): String = propertyValue
}
