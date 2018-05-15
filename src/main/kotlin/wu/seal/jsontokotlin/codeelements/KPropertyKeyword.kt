package wu.seal.jsontokotlin.codeelements

/**
 * keyword relative
 * Created by Seal.Wu on 2017/9/13.
 */

interface IPropertyKeyword {

    val varProperty: String
        get() = "var"
    val valProperty: String
        get() = "val"

    fun get(): String

}

object KPropertyKeyword : wu.seal.jsontokotlin.codeelements.IPropertyKeyword {
    override fun get() = if (wu.seal.jsontokotlin.ConfigManager.isPropertiesVar) wu.seal.jsontokotlin.codeelements.KPropertyKeyword.varProperty else wu.seal.jsontokotlin.codeelements.KPropertyKeyword.valProperty

}