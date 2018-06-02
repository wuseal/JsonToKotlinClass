package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.classblockparse.NestedClassModelClassesCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NormalClassesCodeParser


class KotlinDataClassMaker(private val rootClassName: String, private val json: String) {


    fun makeKotlinDataClasses(): List<KotlinDataClass> {

        val code = KotlinCodeMaker(rootClassName, json).makeKotlinData()
        return if (ConfigManager.isInnerClassModel) {
            listOf(NestedClassModelClassesCodeParser(code).parse())
        } else {
            return NormalClassesCodeParser(code).parse()
        }
    }

}