package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

/**
 * others json lib supporter by custom
 * Created by Seal.Wu on 2017/11/1.
 */

object CustomJsonLibSupporter : IJsonLibSupporter {

    private val propertyAnnotation
        get() = ConfigManager.customAnnotaionFormatString


    override val annotationImportClassString: String
        get() = ConfigManager.customAnnotaionImportClassString

    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val customJsonLibSupportPropertyBuilder = StringBuilder()

        customJsonLibSupportPropertyBuilder.append(CustomJsonLibSupporter.propertyAnnotation.format(rawPropertyName))

        customJsonLibSupportPropertyBuilder.append(" ")

        customJsonLibSupportPropertyBuilder.append(KPropertyKeyword.get())

        customJsonLibSupportPropertyBuilder.append(" ")

        customJsonLibSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        customJsonLibSupportPropertyBuilder.append(": ")

        customJsonLibSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            customJsonLibSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        customJsonLibSupportPropertyBuilder.append(",")

        return customJsonLibSupportPropertyBuilder.toString()
    }
}