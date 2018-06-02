package wu.seal.jsontokotlin.utils.classblockparse

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

import wu.seal.jsontokotlin.test.TestConfig

class NestedClassModelClassesCodeParserTest {

    val tobeParseCode = """data class Data(
    @SerializedName("name") val name: String? = "", // 三班
    @SerializedName("students") val students: List<Student?>? = listOf()
) {
    data class Student(
        @SerializedName("age") val age: Int? = 0, // 25
        @SerializedName("gender") val gender: String? = "", // female
        @SerializedName("grades") val grades: String? = "", // 三班
        @SerializedName("name") val name: String? = "", // 露西
        @SerializedName("score") val score: Score? = Score(),
        @SerializedName("weight") val weight: Weight? = Weight()
    ) {
        data class Score(
            @SerializedName("网络协议") val 网络协议: Int? = 0, // 98
            @SerializedName("JavaEE") val javaEE: Int? = 0, // 92
            @SerializedName("计算机基础") val 计算机基础: Int? = 0 // 93
        )

        data class Weight(
            @SerializedName("value") val value: Double? = 0.0 // 51.3
        ) {
            data class Tips(
                val message: String= "" // 不是很重
            )
        }
    }
}
"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun parse() {
        val kotlinDataClass = NestedClassModelClassesCodeParser(tobeParseCode).parse()
        kotlinDataClass.name.should.be.equal("Data")
        val expected = """data class Data(
    @SerializedName("name")
    val name: String? = "", // 三班
    @SerializedName("students")
    val students: List<Student?>? = listOf()
) {
    data class Student(
        @SerializedName("age")
        val age: Int? = 0, // 25
        @SerializedName("gender")
        val gender: String? = "", // female
        @SerializedName("grades")
        val grades: String? = "", // 三班
        @SerializedName("name")
        val name: String? = "", // 露西
        @SerializedName("score")
        val score: Score? = Score(),
        @SerializedName("weight")
        val weight: Weight? = Weight()
    ) {
        data class Score(
            @SerializedName("网络协议")
            val 网络协议: Int? = 0, // 98
            @SerializedName("JavaEE")
            val javaEE: Int? = 0, // 92
            @SerializedName("计算机基础")
            val 计算机基础: Int? = 0 // 93
        )

        data class Weight(
            @SerializedName("value")
            val value: Double? = 0.0 // 51.3
        ) {
            data class Tips(
                val message: String = "" // 不是很重
            )
        }
    }
}"""
        kotlinDataClass.getCode().should.be.equal(
            expected
        )
    }
}