package wu.seal.jsontokotlin

/**
 * supporter for alibaba fastjson
 * Created by Seal.Wu on 2017/9/28.
 */


object FastjsonSupporter:IJsonLibSupporter {

   internal val annotationImportClassString = "import com.alibaba.fastjson.annotation.JSONField"

   private val propertyAnnotation ="@JSONField(name = \"%s\")"


    override fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String):String {

        val fastjsonSupportPropertyBuilder = StringBuilder()

        fastjsonSupportPropertyBuilder.append(propertyAnnotation.format(rawPropertyName))

        fastjsonSupportPropertyBuilder.append(" ")

        fastjsonSupportPropertyBuilder.append(PropertyKeyword.get())

        fastjsonSupportPropertyBuilder.append(" ")

        fastjsonSupportPropertyBuilder.append(KPropertyName.getName(rawPropertyName))

        fastjsonSupportPropertyBuilder.append(": ")

        fastjsonSupportPropertyBuilder.append(propertyType)

        if (ConfigManager.initWithDefaultValue) {
            fastjsonSupportPropertyBuilder.append(" = ").append(getDefaultValue(propertyType))
        }

        fastjsonSupportPropertyBuilder.append(",")

        return fastjsonSupportPropertyBuilder.toString()
    }


}