package extensions.nstd

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.model.builder.CodeBuilderConfig

/**
 * Created by Nstd on 2020/7/1 13:46.
 */
class CodeBuilderConfigTest {

    //Map<Key, <TestValue, DefaultValue>>
    private val typeTestMap = mapOf<String, Pair<Any, Any>>(
            "test.Int.Min" to Pair(Int.MIN_VALUE, 11),
            "test.Int" to Pair(1, 12),
            "test.Int.Max" to Pair(Int.MAX_VALUE, 13),
            "test.Long.Min" to Pair(Long.MAX_VALUE, 21L),
            "test.Long" to Pair(2L, 22L),
            "test.Long.Max" to Pair(Long.MAX_VALUE, 23L),
            "test.float.Min" to Pair(Float.MIN_VALUE, 31f),
            "test.float" to Pair(3f, 32f),
            "test.Float.Max" to Pair(Float.MAX_VALUE, 33f),
            "test.double.Min" to Pair(Double.MIN_VALUE, 41.0),
            "test.double" to Pair(4.1, 42.0),
            "test.Double.Max" to Pair(Double.MAX_VALUE, 43.0),
            "test.Boolean" to Pair(first = true, second = false),
            "test.Boolean" to Pair(first = false, second = true),
            "test.String" to Pair("test", "default"),
            "test.Array" to Pair(intArrayOf(1, 2, 3), intArrayOf(4, 5)),
            "test.List" to Pair(listOf(4, 5, 6), listOf<Int>()),
            "test.Obj" to Pair(TestObj("Nstd", 123), TestObj("defaultName", 456))
    )

    data class TestObj(val name: String, val age: Int)

    @Test
    fun checkConfig() {
        //check get & set config
        for(item in typeTestMap.entries) {
            CodeBuilderConfig.instance.setConfig(item.key, item.value.first)
            var result = CodeBuilderConfig.instance.getConfig(item.key, item.value.second)
            result.should.equal(item.value.first)
        }

        //check remove config & default value
        for(item in typeTestMap.entries) {
            CodeBuilderConfig.instance.removeConfig(item.key)
            var result = CodeBuilderConfig.instance.getConfig(item.key, item.value.second)
            result.should.equal(item.value.second)
        }
    }
}