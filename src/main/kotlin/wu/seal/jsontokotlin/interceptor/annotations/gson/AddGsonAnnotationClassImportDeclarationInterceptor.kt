package wu.seal.jsontokotlin.interceptor.annotations.gson

import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddGsonAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {


    override fun intercept(originClassImportDeclaration: String): String {


        val propertyAnnotationImportClassString ="import com.google.gson.annotations.SerializedName"


        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
