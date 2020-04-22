package wu.seal.jsontokotlin.utils

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.test.TestConfig

class SimplifiedMethodsKtTest {

    @Before
    fun before() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun getIndentTest() {
        ConfigManager.indent = 4
        val expectedIndent = "    "
        getIndent().should.be.equal(expectedIndent)
    }

    @Test
    fun getClassesStringListTest() {
        val classesStringBlock = """data class Data(
    @SerializedName("a") val a: Int? = 0, // 1
    @SerializedName("b") val b: String? = "", // ss
    @SerializedName("c") val c: C? = C()
)

data class C(
    @SerializedName("m") val m: Int? = 0 // 0
)"""
        val result = getClassesStringList(classesStringBlock)
        result.size.should.be.equal(2)
        result[0].should.be.equal(
            """data class Data(
    @SerializedName("a") val a: Int? = 0, // 1
    @SerializedName("b") val b: String? = "", // ss
    @SerializedName("c") val c: C? = C()
)"""
        )
        result[1].should.be.equal(
            """data class C(
    @SerializedName("m") val m: Int? = 0 // 0
)"""
        )
    }

    @Test
    fun getClassNameFromClassBlockStringTest() {
        val classBlockString = """data class Data(
    @SerializedName("a") val a: Int? = 0, // 1
    @SerializedName("b") val b: String? = "", // ss
    @SerializedName("c") val c: C? = C()
)"""
        getClassNameFromClassBlockString(classBlockString).should.be.equal("Data")
    }


    @Test
    fun replaceClassNameToClassBlockStringTest() {
        val classBlockString = """data class Data(
    @SerializedName("a") val a: Int? = 0, // 1
    @SerializedName("b") val b: String? = "", // ss
    @SerializedName("c") val c: C? = C()
)"""
        val newClassBlockString = """data class DataNew(
    @SerializedName("a") val a: Int? = 0, // 1
    @SerializedName("b") val b: String? = "", // ss
    @SerializedName("c") val c: C? = C()
)"""
        replaceClassNameToClassBlockString(classBlockString, "DataNew").should.be.equal(newClassBlockString)
    }

    @Test
    fun firstIndexAfterSpecificIndexTest() {
        val list = listOf(1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3)
        list.firstIndexAfterSpecificIndex(1,4).should.be.equal(8)
        list.firstIndexAfterSpecificIndex(1,8).should.be.equal(-1)
    }

    @Test
    fun getCommentCode() {
        val comment = "yes\nheihei"
        getCommentCode(comment).should.be.equal("yesheihei")
    }

    @Test
    fun getCommentCode2() {
        val comment = "yes\n\rheihei"
        getCommentCode(comment).should.be.equal("yesheihei")
    }

    @Test
    fun getCommentCode3() {
        val comment = "yes\r\r\n\nheihei"
        getCommentCode(comment).should.be.equal("yesheihei")
    }
}
