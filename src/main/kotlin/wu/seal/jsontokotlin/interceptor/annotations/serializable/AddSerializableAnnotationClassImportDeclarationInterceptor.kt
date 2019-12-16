package wu.seal.jsontokotlin.interceptor.annotations.serializable

import wu.seal.jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddSerializableAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {


        val propertyAnnotationImportClassString = "import kotlinx.serialization.SerialName\n" +
                "import kotlinx.serialization.Serializable"

        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
