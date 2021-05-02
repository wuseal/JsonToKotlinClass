package extensions.nstd

import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Created by Nstd on 2020/7/1 16:12.
 */
interface ICodeBuilderTest<T: KotlinClass> {

    fun setUp()

    fun getData(): T

    fun getExpectedCode(): String

    fun getExpectedCurrentCode(): String
}