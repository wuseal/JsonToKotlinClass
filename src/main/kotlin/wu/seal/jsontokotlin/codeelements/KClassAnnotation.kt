package wu.seal.jsontokotlin.codeelements

import wu.seal.jsontokotlin.ConfigManager
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.supporter.CustomJsonLibSupporter
import wu.seal.jsontokotlin.supporter.LoganSquareSupporter

/**
 * class annotation for json lib
 * Created by Seal.Wu on 2017/11/1.
 */

/**
 * class annotation for json lib
 */
@Deprecated("Try to use interceptor to achive this goal")
interface IClassAnnotation {
    /**
     * get the annotation string be in append before the class name
     */
    fun getClassAnnotation(rawClassName:String):String
}


object KClassAnnotation : IClassAnnotation {

    override fun getClassAnnotation(rawClassName: String): String {

        if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.LoganSquare) {

            return LoganSquareSupporter.getClassAnnotationBlockString(rawClassName)

        }else if (ConfigManager.targetJsonConverterLib == TargetJsonConverter.Custom) {

            return CustomJsonLibSupporter.getClassAnnotationBlockString(rawClassName)
        }

        return ""
    }

}