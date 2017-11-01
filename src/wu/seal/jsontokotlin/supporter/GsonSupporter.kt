package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

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
    wu.seal.jsontokotlin.isTestModel = true
    println("getGsonSupporterProperty:\n ${wu.seal.jsontokotlin.supporter.GsonSupporter.getGsonSupporterProperty("seal is **() good_man ", "Boy")}")
}

object GsonSupporter : wu.seal.jsontokotlin.supporter.IGsonSupporter {

    /**
     * When adapter Gson lib at most ,We should import the Anotation Class
     */
    val annotationImportClassString = "import com.google.gson.annotations.SerializedName"

    internal val anotaionOnProperty = "@SerializedName(\"%s\")"

    override fun getGsonSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val gsonSupportPropertyBuilder = StringBuilder()

        gsonSupportPropertyBuilder.append(wu.seal.jsontokotlin.supporter.GsonSupporter.anotaionOnProperty.format(rawPropertyName))

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(KPropertyKeyword.get())

        gsonSupportPropertyBuilder.append(" ")

        gsonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        gsonSupportPropertyBuilder.append(": ")

        gsonSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            gsonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        gsonSupportPropertyBuilder.append(",")

        return gsonSupportPropertyBuilder.toString()

    }

}
