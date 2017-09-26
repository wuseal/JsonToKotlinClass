package wu.seal.jsontokotlin

/**
 * Gson Support about
 * Created by Sea.Wu on 2017/9/18.
 */

/**
 * When target Json lib is Gson, this prove fun to create Last Property String block
 */
interface IGsonSupporter {
    /**
     * create property String block to fit Gson at most
     */
    fun getGsonSupporterProperty(rawPropertyName: String, propertyType: String): String

}


fun main(args: Array<String>) {
    isTestModel = true
    println("getGsonSupporterProperty:\n ${GsonSupporter.getGsonSupporterProperty("seal is **() good_man ","Boy")}")
}

object GsonSupporter : IGsonSupporter {

    /**
     * When adapter Gson lib at most ,We should import the Anotation Class
     */
    val gsonAnotationImportString = "import com.google.gson.annotations.SerializedName"

    private val anotaionOnProperty = "@SerializedName(\"%s\")"

    override fun getGsonSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val gsonSupportPropertyBuilder = StringBuilder()

        gsonSupportPropertyBuilder.append(anotaionOnProperty.format(rawPropertyName))

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(PropertyKeyword.get())

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        gsonSupportPropertyBuilder.append(": ")

        gsonSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            gsonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        gsonSupportPropertyBuilder.append(",")

        return gsonSupportPropertyBuilder.toString()

    }

}
