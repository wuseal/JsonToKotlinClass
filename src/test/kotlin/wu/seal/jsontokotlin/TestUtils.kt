package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.model.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.KotlinClassMaker

/**
 * Generate kotlin data classes with root name 'Test'
 */
fun String.generateKotlinDataClass(className: String = "Test") =
    KotlinClassMaker(className, this).makeKotlinClass() as KotlinDataClass


fun KotlinDataClass.applyInterceptor(interceptor: IKotlinDataClassInterceptor): KotlinDataClass {
    return applyInterceptors(listOf(interceptor))
}