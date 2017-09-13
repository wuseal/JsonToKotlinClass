package wu.seal.jsontokotlin

/**
 * Created by LENOVO on 2017/9/13.
 */

interface IPropertyKeyword {

    val varProperty: String
        get() = "var"
    val valProperty: String
        get() = "val"

    fun get(): String

}

object PropertyKeyword : IPropertyKeyword {
    override fun get() = if (ConfigManager.isPropertiesVar) varProperty else valProperty

}