package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.ConfigManager


/**
 * insert parent class declaration code
 */
class ParentClassImportClassDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originImportClasses: String): String {
        val parentClassImportDeclaration = "import ${ConfigManager.parenClassTemplate.substringBeforeLast("(").trim()}"
        return "$originImportClasses\n$parentClassImportDeclaration".trim()
    }
}