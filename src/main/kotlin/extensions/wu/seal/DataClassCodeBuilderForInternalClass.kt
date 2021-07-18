package extensions.wu.seal

import wu.seal.jsontokotlin.model.builder.IKotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

/**
 * kotlin class code generator with internal modifier before class
 *
 * Created by Seal on 2020/7/7 21:40.
 */
class DataClassCodeBuilderForInternalClass(private val kotlinDataClassCodeBuilder: IKotlinDataClassCodeBuilder) :
    IKotlinDataClassCodeBuilder {

    override fun DataClass.genClassName(): String {
        val originClassName = kotlinDataClassCodeBuilder.run { genClassName() }
        return "internal $originClassName"
    }
    override fun DataClass.genClassComment(): String {
        return kotlinDataClassCodeBuilder.run { genClassComment() }
    }

    override fun DataClass.genClassAnnotations(): String {
        return kotlinDataClassCodeBuilder.run { genClassAnnotations() }
    }

    override fun DataClass.genParentClass(): String {
        return kotlinDataClassCodeBuilder.run { genParentClass() }
    }

    override fun DataClass.genBody(): String {
        return kotlinDataClassCodeBuilder.run { genBody() }
    }

    override fun DataClass.genPrimaryConstructorProperties(): String {
        return kotlinDataClassCodeBuilder.run { genPrimaryConstructorProperties() }
    }
}
