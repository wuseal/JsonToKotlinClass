package extensions.wu.seal

import wu.seal.jsontokotlin.model.builder.IKotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

/**
 * Created by Nstd on 2021/8/6 11:17.
 */
abstract class BaseDataClassCodeBuilder(private val kotlinDataClassCodeBuilder: IKotlinDataClassCodeBuilder)
    : IKotlinDataClassCodeBuilder by kotlinDataClassCodeBuilder {

    override fun genPrimaryConstructor(clazz: DataClass): String = super.genPrimaryConstructor(clazz)
    override fun getCode(clazz: DataClass): String = super.getCode(clazz)
    override fun getOnlyCurrentCode(clazz: DataClass): String = super.getOnlyCurrentCode(clazz)
}