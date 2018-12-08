package wu.seal.jsontokotlin

import wu.seal.jsontokotlin.classscodestruct.KotlinDataClass
import wu.seal.jsontokotlin.utils.classblockparse.NestedClassModelClassesCodeParser
import wu.seal.jsontokotlin.utils.classblockparse.NormalClassesCodeParser
import com.intellij.psi.PsiDirectory
import wu.seal.jsontokotlin.utils.ClassCodeFilter
import wu.seal.jsontokotlin.utils.KotlinDataClassFileGenerator
import wu.seal.jsontokotlin.utils.classblockparse.ParsedKotlinDataClass

class KotlinDataClassMaker(private val rootClassName: String, private val json: String, private val directory: PsiDirectory) {


    fun makeKotlinDataClasses(): List<KotlinDataClass> {

        val codeMaker = KotlinCodeMaker(rootClassName, json)

        //code string after removing duplicate class structure code
        val code = ClassCodeFilter.removeDuplicateClassCode(codeMaker.makeKotlinData())

        return if (ConfigManager.isInnerClassModel) {
            listOf(NestedClassModelClassesCodeParser(code).parse())
        } else {

            // reusing KotlinDataClassFileGenerator() to create the list of non duplicate classes
            val parsedKotlinClasses: List<ParsedKotlinDataClass> =
                    KotlinDataClassFileGenerator().makeKotlinDataClasses(code, directory)

            return parsedKotlinClasses.map { KotlinDataClass.fromParsedKotlinDataClass(it) }
        }
    }

}