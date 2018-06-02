package wu.seal.jsontokotlin.utils.classblockparse

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.getClassesStringList

class NormalClassesCodeParser(private val classesCode: String) {
    fun parse(): List<KotlinDataClass> {
        return getClassesStringList(classesCode)
            .map {
                KotlinDataClass.fromParsedKotlinDataClass(ClassCodeParser(it).getKotlinDataClass())
            }
    }
}