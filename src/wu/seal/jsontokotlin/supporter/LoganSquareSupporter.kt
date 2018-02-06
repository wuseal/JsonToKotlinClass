package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

/**
 * LoganSquare Json Lib supporter file
 * Created by Seal.Wu on 2017/11/1.
 */

object LoganSquareSupporter : IJsonLibSupporter {

    val classAnnotation = "@JsonObject"
    private val propertyAnnotation = "@JsonField(name = arrayOf(\"%s\"))"

    override val annotationImportClassString: String
        get() = "import com.bluelinelabs.logansquare.annotation.JsonField\nimport com.bluelinelabs.logansquare.annotation.JsonObject"


    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val loganSquareSupportPropertyBuilder = StringBuilder()

        loganSquareSupportPropertyBuilder.append(LoganSquareSupporter.propertyAnnotation.format(rawPropertyName))

        loganSquareSupportPropertyBuilder.append(" ")

        loganSquareSupportPropertyBuilder.append(KPropertyKeyword.get())

        loganSquareSupportPropertyBuilder.append(" ")

        loganSquareSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        loganSquareSupportPropertyBuilder.append(": ")

        loganSquareSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            loganSquareSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        loganSquareSupportPropertyBuilder.append(",")

        return loganSquareSupportPropertyBuilder.toString()

    }

}