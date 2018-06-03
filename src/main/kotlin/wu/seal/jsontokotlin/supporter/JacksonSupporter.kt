package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.utils.getIndent

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

object JacksonSupporter : IJacksonSupporter {

    private val indent = lazy { getIndent() }

    val anotaionFormat = "@JsonProperty(\"%s\")"

    override fun getJacksonSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val jacksonSupportPropertyBuilder = StringBuilder()

        jacksonSupportPropertyBuilder.append(indent.value)

        jacksonSupportPropertyBuilder.append(JacksonSupporter.anotaionFormat.format(rawPropertyName))

        jacksonSupportPropertyBuilder.append(" ")

        jacksonSupportPropertyBuilder.append(KPropertyKeyword.get())

        jacksonSupportPropertyBuilder.append(" ")

        jacksonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        jacksonSupportPropertyBuilder.append(": ")

        jacksonSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            jacksonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        return jacksonSupportPropertyBuilder.toString()
    }

}