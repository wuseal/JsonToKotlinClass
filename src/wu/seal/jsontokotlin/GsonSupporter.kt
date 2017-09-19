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
    fun getGsonSupportorProperty(rawPropertyName: String, propertyType: String): String

}


object GsonSupporter : IGsonSupporter {

    /**
     * When adapter Gson lib at most ,We should import the Anotation Class
     */
    val gsonAnotationImportString = "import com.google.gson.annotations.SerializedName"

    private val anotaionOnProperty = "@SerializedName(\"%s\")"

    override fun getGsonSupportorProperty(rawPropertyName: String, propertyType: String): String {

        val gsonSupportPropertyBuilder = StringBuilder()

        gsonSupportPropertyBuilder.append(anotaionOnProperty.format(rawPropertyName))

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(PropertyKeyword.get())

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(PropertyNameMaker.makePropertyName(rawPropertyName, true))

        gsonSupportPropertyBuilder.append(": ")

        gsonSupportPropertyBuilder.append(propertyType)

        gsonSupportPropertyBuilder.append(",")

        return gsonSupportPropertyBuilder.toString()

    }

}
