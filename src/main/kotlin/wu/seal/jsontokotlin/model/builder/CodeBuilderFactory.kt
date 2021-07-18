//package wu.seal.jsontokotlin.model.builder
//
//import wu.seal.jsontokotlin.model.classscodestruct.DataClass
//import wu.seal.jsontokotlin.model.classscodestruct.EnumClass
//import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
//import wu.seal.jsontokotlin.model.classscodestruct.ListClass
//
///**
// * support element type
// */
//sealed class ElementType
//object TYPE_CLASS : ElementType()
//object TYPE_ENUM  : ElementType()
//object TYPE_LIST  : ElementType()
//
///**
// * support language
// */
//sealed class Language
//object LANG_KOTLIN : Language()
//
///**
// * Created by Nstd on 2020/6/30 17:50.
// */
//object CodeBuilderFactory {
//
//    /**
//     * language config key, if new language is added, extract this key to the extension
//     */
//    private const val CONF_LANGUAGE = "code.builder.language"
//
//    fun <T : KotlinClass> get(type: ElementType, data: T): ICodeBuilder {
//        var lang: Language = CodeBuilderConfig.instance.getConfig(CONF_LANGUAGE, LANG_KOTLIN)
//        return when(type) {
//            TYPE_CLASS -> getClassCodeBuilder(lang, data as DataClass)
//            TYPE_ENUM  -> getEnumCodeBuilder(lang, data as EnumClass)
//            TYPE_LIST  -> getListCodeBuilder(lang, data as ListClass)
//        }
//    }
//
//    private fun getClassCodeBuilder(lang: Language, data: DataClass): ICodeBuilder {
//        return when(lang) {
//            LANG_KOTLIN -> KotlinDataClassCodeBuilder()
//        }
//    }
//
//    private fun getEnumCodeBuilder(lang: Language, data: EnumClass): ICodeBuilder {
//        return when(lang) {
//            LANG_KOTLIN -> KotlinEnumCodeBuilder(data)
//        }
//    }
//
//    private fun getListCodeBuilder(lang: Language, data: ListClass): ICodeBuilder {
//        return when(lang) {
//            LANG_KOTLIN -> KotlinListCodeBuilder(data)
//        }
//    }
//}