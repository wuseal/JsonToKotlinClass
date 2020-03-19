package wu.seal.jsontokotlin.interceptor.annotations.custom

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddCustomAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {


        val propertyAnnotationImportClassString = ConfigManager.customAnnotationClassImportdeclarationString


        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
