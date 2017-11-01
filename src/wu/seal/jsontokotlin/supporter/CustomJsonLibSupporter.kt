package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

/**
 * others json lib supporter by custom
 * Created by Seal.Wu on 2017/11/1.
 */

object CustomJsonLibSupporter : IJsonLibSupporter {

    private val propertyAnnotation = wu.seal.jsontokotlin.ConfigManager.customAnnotaionFormatString


    override val annotationImportClassString: String
        get() = wu.seal.jsontokotlin.ConfigManager.customAnnotaionImportClassString

    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val customJsonLibSupportPropertyBuilder = StringBuilder()

        customJsonLibSupportPropertyBuilder.append(wu.seal.jsontokotlin.supporter.CustomJsonLibSupporter.propertyAnnotation.format(rawPropertyName))

        customJsonLibSupportPropertyBuilder.append(" ")

        customJsonLibSupportPropertyBuilder.append(KPropertyKeyword.get())

        customJsonLibSupportPropertyBuilder.append(" ")

        customJsonLibSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        customJsonLibSupportPropertyBuilder.append(": ")

        customJsonLibSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            customJsonLibSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        customJsonLibSupportPropertyBuilder.append(",")

        return customJsonLibSupportPropertyBuilder.toString()
    }
}