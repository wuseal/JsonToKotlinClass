package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue
import wu.seal.jsontokotlin.utils.getIndent

/**
 * supporter for alibaba fastjson
 * Created by Seal.Wu on 2017/9/28.
 */


object FastjsonSupporter : IJsonLibSupporter {


    override val annotationImportClassString: String
        get() = "import com.alibaba.fastjson.annotation.JSONField"

    const val propertyAnnotationFormat = "@JSONField(name = \"%s\")"


    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String {

        val fastjsonSupportPropertyBuilder = StringBuilder()

        fastjsonSupportPropertyBuilder.append(getIndent())

        fastjsonSupportPropertyBuilder.append(FastjsonSupporter.propertyAnnotationFormat.format(rawPropertyName))

        fastjsonSupportPropertyBuilder.append(" ")

        fastjsonSupportPropertyBuilder.append(KPropertyKeyword.get())

        fastjsonSupportPropertyBuilder.append(" ")

        /**
         * todo // the logic below also has problem ,remove 'is' when it end with uppercase,or don't do anything
         */
        fastjsonSupportPropertyBuilder.append(KPropertyName.getName(removeStartIsCharactors(rawPropertyName)))

        fastjsonSupportPropertyBuilder.append(": ")

        fastjsonSupportPropertyBuilder.append(propertyType)

        if (wu.seal.jsontokotlin.ConfigManager.initWithDefaultValue) {
            fastjsonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        return fastjsonSupportPropertyBuilder.toString()
    }


    private fun removeStartIsCharactors(rawPropertyName: String): String {
        return if (rawPropertyName.startsWith("is")) {
            var modifyPropertyName = rawPropertyName.removePrefix("is")
            while (modifyPropertyName.startsWith("is")) {
                modifyPropertyName = modifyPropertyName.removePrefix("is")
            }
            modifyPropertyName
        } else {
            rawPropertyName
        }
    }
}
