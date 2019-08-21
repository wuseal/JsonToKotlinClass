package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.interceptor.IKotlinDataClassInterceptor
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NestedClassModelClassesCodeParser

/**
 * Generate kotlin data classes with root name 'Test'
 */
fun String.generateKotlinDataClass(className: String = "Test") =
        NestedClassModelClassesCodeParser(KotlinCodeMaker(className, this).makeKotlinData()).parse()


fun KotlinDataClass.applyInterceptor(interceptor: IKotlinDataClassInterceptor): KotlinDataClass {
    return applyInterceptors(listOf(interceptor))
}