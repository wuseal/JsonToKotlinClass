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

