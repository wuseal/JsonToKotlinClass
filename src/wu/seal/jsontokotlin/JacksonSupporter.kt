package wu.seal.jsontokotlin

/**
 * Jackson json lib supporter
 * Created by Seal.Wu on 2017/9/27.
 */

interface IJacksonSupporter {

    val annotationImportClassString: String
        get() = "import com.fasterxml.jackson.annotation.JsonProperty"

    /**
     * get the jackson supporter property string block
     */
    fun getJacksonSupporterProperty(rawPropertyName: String, propertyType: String): String
}

fun main(args: Array<String>) {
    isTestModel = true
    println("getGsonSupporterProperty:\n ${JacksonSupporter.getJacksonSupporterProperty("seal is **() good_man ","Boy")}")
}

object JacksonSupporter : IJacksonSupporter {

    private val anotaionOnProperty = "@JsonProperty(\"%s\")"

    override fun getJacksonSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val jacksonSupportPropertyBuilder = StringBuilder()

        jacksonSupportPropertyBuilder.append(anotaionOnProperty.format(rawPropertyName))

        jacksonSupportPropertyBuilder.append(" ")

        jacksonSupportPropertyBuilder.append(PropertyKeyword.get())

        jacksonSupportPropertyBuilder.append(" ")

        jacksonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        jacksonSupportPropertyBuilder.append(": ")

        jacksonSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            jacksonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        jacksonSupportPropertyBuilder.append(",")

        return jacksonSupportPropertyBuilder.toString()
    }

}