package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.utils.getIndent

/**
 * LoganSquare Json Lib supporter file
 * Created by Seal.Wu on 2017/11/1.
 */

object LoganSquareSupporter : IJsonLibSupporter {

    private val indent = lazy { getIndent() }

    internal val classAnnotation = "@JsonObject"
    internal val propertyAnnotationFormat = "@JsonField(name = arrayOf(\"%s\"))"

    override val annotationImportClassString: String
        get() = "import com.bluelinelabs.logansquare.annotation.JsonField\nimport com.bluelinelabs.logansquare.annotation.JsonObject"


    override fun getClassAnnotationBlockString(rawClassName: String): String {
        return classAnnotation
    }
    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val loganSquareSupportPropertyBuilder = StringBuilder()

        loganSquareSupportPropertyBuilder.append(indent.value)

        loganSquareSupportPropertyBuilder.append(LoganSquareSupporter.propertyAnnotationFormat.format(rawPropertyName))

        loganSquareSupportPropertyBuilder.append(" ")

        loganSquareSupportPropertyBuilder.append(KPropertyKeyword.get())

        loganSquareSupportPropertyBuilder.append(" ")

        loganSquareSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        loganSquareSupportPropertyBuilder.append(": ")

        loganSquareSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            loganSquareSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        return loganSquareSupportPropertyBuilder.toString()

    }

}