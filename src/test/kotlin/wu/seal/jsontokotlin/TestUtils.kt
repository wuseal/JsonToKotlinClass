package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.utils.KotlinClassMaker

/**
 * Generate kotlin data classes with root name 'Test'
 */
fun String.generateKotlinDataClass(className: String = "Test") =
    KotlinClassMaker(className, this).makeKotlinClass() as DataClass


/**
 * Generate kotlin classes with root name 'Test'
 */
fun String.generateKotlinClass(className: String = "Test") =
        KotlinClassMaker(className, this).makeKotlinClass()

/**
 * Generate kotlin classes code with root name 'Test'
 */
fun String.generateKotlinClassCode(className: String = "Test") =
        KotlinCodeMaker(className, this).makeKotlinData()