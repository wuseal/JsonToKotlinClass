package wu.seal.jsontokotlin.supporter

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.codeelements.KPropertyKeyword
import wu.seal.jsontokotlin.codeelements.KPropertyName
import wu.seal.jsontokotlin.codeelements.getDefaultValue

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */


object NoneWithCamelCaseSupporter : INoneLibSupporter {

    override fun getNoneLibSupporterClassName(rawClassName: String):String {
        return ""
    }


    override fun getNoneLibSupporterProperty(rawPropertyName: String, propertyType: String): String {

        val blockBuilder = StringBuilder()

        blockBuilder.append(KPropertyKeyword.get())
        blockBuilder.append(" ")
        blockBuilder.append(KPropertyName.getName(rawPropertyName))
        blockBuilder.append(": ").append(propertyType)
        if (ConfigManager.initWithDefaultValue) {
            blockBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        return blockBuilder.toString()
    }

}

