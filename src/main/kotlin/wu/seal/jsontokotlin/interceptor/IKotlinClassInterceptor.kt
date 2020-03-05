package wu.seal.jsontokotlin.interceptor

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Interceptor for kotlin class code transform
 */
interface IKotlinClassInterceptor<out T : KotlinClass> {

    /**
     * intercept the kotlin class and modify the class,the function will return a new  Kotlin Class Object
     * warn: the new returned object  is a new object ,not the original
     */
    fun intercept(kotlinClass: KotlinClass): T

}