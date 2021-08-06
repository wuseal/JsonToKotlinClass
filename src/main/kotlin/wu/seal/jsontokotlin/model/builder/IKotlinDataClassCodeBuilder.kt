package wu.seal.jsontokotlin.model.builder

import wu.seal.jsontokotlin.model.classscodestruct.DataClass

interface IKotlinDataClassCodeBuilder : ICodeBuilder<DataClass> {
    fun DataClass.genClassComment(): String
    fun DataClass.genClassAnnotations(): String
    fun DataClass.genClassName(): String
    fun DataClass.genParentClass(): String
    fun DataClass.genBody(): String
    fun DataClass.genPrimaryConstructorProperties(): String
    fun String.executeWhenNotBlank(call: (String) -> Unit) = if (isNotBlank()) call(this) else Unit

    object EmptyImpl: IKotlinDataClassCodeBuilder {
        override fun getCode(clazz: DataClass): String {
            return ""
        }

        override fun getOnlyCurrentCode(clazz: DataClass): String {
            return ""
        }

        override fun DataClass.genClassComment(): String {
            return ""
        }

        override fun DataClass.genClassAnnotations(): String {
            return ""
        }

        override fun DataClass.genClassName(): String {
            return ""
        }

        override fun DataClass.genParentClass(): String {
            return ""
        }

        override fun DataClass.genBody(): String {
            return ""
        }

        override fun DataClass.genPrimaryConstructorProperties(): String {
            return ""
        }
    }
}