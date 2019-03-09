package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.classblockparse.ClassCodeParser

/**
 * Generate kotlin data classes with root name 'Test'
 */
fun String.generateKotlinDataClass() =
        KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(KotlinCodeMaker("Test", this).makeKotlinData()).getKotlinDataClass())