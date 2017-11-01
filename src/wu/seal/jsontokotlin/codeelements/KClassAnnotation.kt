package wu.seal.jsontokotlin.codeelements

/**
 * class annotation for json lib
 * Created by Seal.Wu on 2017/11/1.
 */

/**
 * class annotation for json lib
 */
interface IClassAnnotation {
    /**
     * get the annotation string be in append before the class name
     */
    fun getClassAnnotation():String
}


object KClassAnnotation : wu.seal.jsontokotlin.codeelements.IClassAnnotation {
    override fun getClassAnnotation(): String {
        if (wu.seal.jsontokotlin.ConfigManager.targetJsonConverterLib == wu.seal.jsontokotlin.TargetJsonConverter.LoganSquare) {
            return wu.seal.jsontokotlin.supporter.LoganSquareSupporter.classAnnotation
        }

        return ""
    }

}