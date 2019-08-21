package wu.seal.jsontokotlin.interceptor.annotations.fastjson

import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddFastjsonAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {


        val propertyAnnotationImportClassString = "import com.alibaba.fastjson.annotation.JSONField"


        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }

}
