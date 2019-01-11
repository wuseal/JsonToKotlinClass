package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.supporter.MoShiSupporter

/**
 * try to add import class declarations of Moshi Generation
 */
class AddMoshiCodeGenClassDeclarationInterceptor :IImportClassDeclarationInterceptor{

    override fun intercept(originImportClasses: String): String {

        val propertyAnnotationImportClassString = MoShiSupporter.annotationImportClassString

        val classAnnotationImportClassString = "import com.squareup.moshi.JsonClass"

        return propertyAnnotationImportClassString.plus("\n").plus(classAnnotationImportClassString)
    }
}
