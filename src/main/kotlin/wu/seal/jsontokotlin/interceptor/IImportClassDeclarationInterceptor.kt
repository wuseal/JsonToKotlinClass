package wu.seal.jsontokotlin.interceptor

/**
 * Insert import class code
 */
interface IImportClassDeclarationInterceptor {

    /**
     * intercept the import class declaration code insert to the origin import classes declaration code
     */
    fun intercept(originClassImportDeclaration: String): String


    /**
     * append class import declaration behind original string
     */
    fun String.append(classImportDeclaration: String): String {

        return plus("\n").plus(classImportDeclaration)
    }

}