package wu.seal.jsontokotlin.interceptor.annotations.moshi

import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddMoshiAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {


    override fun intercept(originClassImportDeclaration: String): String {


        val propertyAnnotationImportClassString =  "import com.squareup.moshi.Json"


        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
