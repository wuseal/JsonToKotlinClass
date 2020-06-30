package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.EnumClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.ListClass

/**
 * Created by Nstd on 2020/6/30 17:50.
 */
object CodeBuilderFactory {

    fun <T : KotlinClass> get(type: String, lang: String, data: T): ICodeBuilder {
        return when(type) {
            "class" -> getClassCodeBuilder(lang, data as DataClass)
            "enum" -> KotlinEnumCodeBuilder(data as EnumClass)
            "list" -> KotlinListCodeBuilder(data as ListClass)
            else -> EmptyCodeBuilder()
        }
    }

    private fun getClassCodeBuilder(lang: String, data: DataClass): ICodeBuilder {
        return when(lang) {
            "kotlin" -> KotlinCodeBuilder(data)
            "java" -> JavaCodeBuilder(data)
            else -> JavaCodeBuilder(data)
        }
    }
}