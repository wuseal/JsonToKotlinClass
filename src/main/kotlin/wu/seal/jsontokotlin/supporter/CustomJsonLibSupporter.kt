package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.utils.getIndent
import wu.seal.jsontokotlin.utils.numberOf

/**
 * others json lib supporter by custom
 * Created by Seal.Wu on 2017/11/1.
 */

object CustomJsonLibSupporter : IJsonLibSupporter {


    private val propertyAnnotation
        get() = ConfigManager.customPropertyAnnotationFormatString


    private val classAnnotationFormat
        get() = ConfigManager.customClassAnnotationFormatString

    override val annotationImportClassString: String
        get() = ConfigManager.customAnnotationClassImportdeclarationString


    override fun getClassAnnotationBlockString(rawClassName: String): String {
        return if (classAnnotationFormat.contains("%s")) {
            val count = classAnnotationFormat.numberOf("%s")
            val args = arrayOfNulls<String>(count).apply { fill(rawClassName) }
            classAnnotationFormat.format(*args)
        } else {
            classAnnotationFormat
        }
    }

    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val customJsonLibSupportPropertyBuilder = StringBuilder()


        val propertyAnnotationString = getPropertyAnnotationString(rawPropertyName)

        propertyAnnotationString.split("\n").forEach {
            customJsonLibSupportPropertyBuilder.append(getIndent())
            customJsonLibSupportPropertyBuilder.append(it)
            customJsonLibSupportPropertyBuilder.append("\n")
        }

        customJsonLibSupportPropertyBuilder.append(getIndent())

        customJsonLibSupportPropertyBuilder.append(KPropertyKeyword.get())

        customJsonLibSupportPropertyBuilder.append(" ")

        val propertyName =
            if (needRenamePropertyNameToCamelCase()) KPropertyName.getName(rawPropertyName) else rawPropertyName

        customJsonLibSupportPropertyBuilder.append(propertyName)

        customJsonLibSupportPropertyBuilder.append(": ")

        customJsonLibSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            customJsonLibSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        return customJsonLibSupportPropertyBuilder.toString()
    }

    fun getPropertyAnnotationString(rawPropertyName: String): String {

        return if (propertyAnnotation.contains("%s")) {

            val count = propertyAnnotation.numberOf("%s")
            val args = arrayOfNulls<String>(count).apply { fill(rawPropertyName) }

            propertyAnnotation.format(*args)

        } else {
            propertyAnnotation
        }

    }

    private fun needRenamePropertyNameToCamelCase() = propertyAnnotation.contains("%s")
}
