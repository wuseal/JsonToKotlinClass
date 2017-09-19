package wu.seal.jsontokotlin

/**
 * Created by Seal.Wu on 2017/9/18.
 */

interface IProperty {
    /**
     *
     */
    fun getPropertyStringBlock(): String

}

class KProperty(val rawPropertyName: String, val propertyType: String, val propertyValue: String) : IProperty {


    override fun getPropertyStringBlock(): String {
        val blockBulder = StringBuilder()

        blockBulder.append("\t\t")


        if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.None) {

            blockBulder.append(NoneSupporter.getNoneLibSupporterProperty(rawPropertyName, propertyType))

        } else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Gson) {

            blockBulder.append(GsonSupporter.getGsonSupportorProperty(rawPropertyName, propertyType))
        }

        if (!ConfigManager.isCommentOff && propertyValue.isNotBlank()) {
            blockBulder.append(" //").append(propertyValue)
        }

        return blockBulder.toString()
    }

}