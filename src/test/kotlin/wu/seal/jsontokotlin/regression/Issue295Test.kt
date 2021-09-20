package wu.seal.jsontokotlin.regression

import com.winterbe.expekt.should
import org.junit.Test
import wu.seal.jsontokotlin.KotlinCodeMaker
import wu.seal.jsontokotlin.test.TestConfig

class Issue295Test {

    @Test
    fun testDoubleTypeInArray() {

        TestConfig.setToTestInitState()

        val json = """
            {
            "arr": [10, ${Long.MAX_VALUE}, 11.2]
            }
        """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData().trimIndent()
        val expectCode = """
            data class A(
                @SerializedName("arr")
                val arr: List<Double> = listOf()
            )
        """.trimIndent()
        resultCode.should.be.equal(expectCode)
    }

    @Test
    fun testLongTypeInArray() {

        TestConfig.setToTestInitState()

        val json = """
            {
             "arr": [10, ${Long.MAX_VALUE}]
            }
        """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData().trimIndent()
        val expectCode = """
            data class A(
                @SerializedName("arr")
                val arr: List<Long> = listOf()
            )
        """.trimIndent()
        resultCode.should.be.equal(expectCode)
    }

    @Test
    fun testDoubleTypeInObject() {

        TestConfig.setToTestInitState()

        val json = """
           {
             "arr": [
               {
                 "key": 10
               },
               {
                 "key": ${Long.MAX_VALUE}
               },
               {
                 "key": 11.2
               }
             ]
           }
        """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData().trimIndent()
        val expectCode = """
            data class A(
                @SerializedName("arr")
                val arr: List<Arr> = listOf()
            ) {
                data class Arr(
                    @SerializedName("key")
                    val key: Double = 0.0 // 11.2
                )
            }
        """.trimIndent()
        resultCode.should.be.equal(expectCode)
    }

    @Test
    fun testLongTypeInObject() {

        TestConfig.setToTestInitState()

        val json = """
           {
             "arr": [
               {
                 "key": 10
               },
               {
                 "key": ${Long.MAX_VALUE}
               }
             ]
           }
        """.trimIndent()
        val resultCode = KotlinCodeMaker("A", json).makeKotlinData().trimIndent()
        val expectCode = """
            data class A(
                @SerializedName("arr")
                val arr: List<Arr> = listOf()
            ) {
                data class Arr(
                    @SerializedName("key")
                    val key: Long = 0 // 9223372036854775807
                )
            }
        """.trimIndent()
        resultCode.should.be.equal(expectCode)
    }
}