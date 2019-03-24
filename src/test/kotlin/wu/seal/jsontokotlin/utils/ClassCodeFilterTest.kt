package wu.seal.jsontokotlin.utils

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

/**
 * Created by Seal.Wu on 2018/4/18.
 */
class ClassCodeFilterTest {

    private val withDuplicateClassString = """data class Test(
		val user_id: String = "",
		val password: String = "",
		val gender: Int = 0,
		val birthday: String = "",
		val phone: String = "",
		val name: String = "",
		val cons: Int = 0,
		val gps_info: String = "",
		val activity_time: String = "",
		val sign: String = "",
		val vip: String = "",
		val being_liked_num: Int = 0,
		val info: Info = Info(),
		val tag: List<String> = listOf(),
		val interest: Interest = Interest(),
		val answer: List<Answer> = listOf(),
		val media: List<Media> = listOf(),
		val friend_show: FriendShow = FriendShow()
)

data class Info(
		val industry: String = "",
		val work: String = "",
		val comp: String = "",
		val city: String = "",
		val hauntedly: String = ""
)

data class Interest(
		val sports: List<String> = listOf(),
		val music: List<String> = listOf(),
		val food: List<String> = listOf(),
		val movie: List<String> = listOf(),
		val books_and_comic: List<String> = listOf(),
		val footprint: List<String> = listOf()
)

data class FriendShow(
		val cover: String = "",
		val show_list: List<Show> = listOf()
)

data class Show(
		val media: List<Media> = listOf(),
		val time: String = "",
		val title: String = ""
)

data class Media(
		val type: Int = 0,
		val url: String = "",
		val order: Int = 0
)

data class Media(
		val type: Int = 0,
		val url: String = "",
		val order: Int = 0
)

data class Answer(
		val a: String = "",
		val q: String = ""
)"""

    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun removeDuplicateClassCode() {
        TestConfig.isNestedClassModel = false
        withDuplicateClassString.indexOf("data class Media(").should.not.be.equal(withDuplicateClassString.lastIndexOf("data class Media("))
        val removeDuplicateClassCode = ClassCodeFilter.removeDuplicateClassCode(withDuplicateClassString)
        removeDuplicateClassCode.indexOf("data class Media(").should.be.equal(removeDuplicateClassCode.lastIndexOf("data class Media("))

        println(removeDuplicateClassCode)
    }
}