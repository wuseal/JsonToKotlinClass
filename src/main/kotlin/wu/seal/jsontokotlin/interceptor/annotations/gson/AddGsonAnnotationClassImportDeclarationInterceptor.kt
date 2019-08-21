package wu.seal.jsontokotlin.interceptor.annotations.gson

import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddGsonAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    companion object{

        const val propertyAnnotationImportClassString ="import com.google.gson.annotations.SerializedName"
    }

    override fun intercept(originClassImportDeclaration: String): String {

        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
