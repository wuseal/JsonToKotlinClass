package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

/**
 * MoShiSupporter File
 * Created by Seal.Wu on 2017/10/31.
 */

object MoShiSupporter : IJsonLibSupporter {
    override val annotationImportClassString: String
        get() = "import com.squareup.moshi.Json"


    private val propertyAnnotation = "@Json(name = \"%s\")"


    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {
        val moShijsonSupportPropertyBuilder = StringBuilder()

        moShijsonSupportPropertyBuilder.append(MoShiSupporter.propertyAnnotation.format(rawPropertyName))

        moShijsonSupportPropertyBuilder.append(" ")

        moShijsonSupportPropertyBuilder.append(KPropertyKeyword.get())

        moShijsonSupportPropertyBuilder.append(" ")

        moShijsonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        moShijsonSupportPropertyBuilder.append(": ")

        moShijsonSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            moShijsonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        moShijsonSupportPropertyBuilder.append(",")

        return moShijsonSupportPropertyBuilder.toString()
    }

}