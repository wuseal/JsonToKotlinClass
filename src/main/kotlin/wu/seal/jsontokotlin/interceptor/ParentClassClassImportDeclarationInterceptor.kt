package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.utils.KOTLIN_KEYWORD_LIST


/**
 * insert parent class declaration code
 */
class ParentClassClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {

        val importClass = ConfigManager.parenClassTemplate.substringBeforeLast("(").trim()

        val legalImportClass = importClass.split(".").map {
            if ((KOTLIN_KEYWORD_LIST.contains(it) || it.first().isDigit())) "`$it`" else it
        }.joinToString(".")

        val parentClassImportDeclaration = "import $legalImportClass"

        return "$originClassImportDeclaration\n$parentClassImportDeclaration".trim()
    }
}