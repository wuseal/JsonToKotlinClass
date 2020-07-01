package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.model.builder.*
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.EnumClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.model.classscodestruct.ListClass

/**
 * Created by Nstd on 2020/7/1 16:10.
 */
class CodeBuilderFactoryTest {

    val allTypedTest = mapOf(
            TYPE_CLASS to KotlinCodeBuilderTest(),
            TYPE_ENUM to KotlinEnumCodeBuilderTest(),
            TYPE_LIST to KotlinListCodeBuilderTest()
    )

    @Test
    fun testFactory() {
        for(type in allTypedTest.entries) {
            checkBuilder(type.key, type)
        }
    }

    private fun <T: KotlinClass> Map.Entry<ElementType, ICodeBuilderTest<T>>.doTestCheck(): Boolean {
        this.value.setUp()
        CodeBuilderFactory.get(this.key, this.value.getData()).getCode().should.be.equal(this.value.getExpectedCode())
        return true
    }

    /**
     * ensure all type is checked
     */
    private fun checkBuilder(type: ElementType, item: Map.Entry<ElementType, ICodeBuilderTest<*>>): Boolean {
        return when(type) {
            TYPE_CLASS -> (item as Map.Entry<ElementType, ICodeBuilderTest<DataClass>>).doTestCheck()
            TYPE_ENUM -> (item as Map.Entry<ElementType, ICodeBuilderTest<EnumClass>>).doTestCheck()
            TYPE_LIST -> (item as Map.Entry<ElementType, ICodeBuilderTest<ListClass>>).doTestCheck()
        }
    }

}