package wu.seal.jsontokotlin.supporter

/**
 *
 * Created by Seal.Wu on 2017/9/28.
 */
interface IJsonLibSupporter {

    val annotationImportClassString: String

    fun getClassAnnotationBlockString(rawClassName: String): String = ""

    fun getJsonLibSupportPropertyBlockString(rawPropertyName: String, propertyType: String): String
}