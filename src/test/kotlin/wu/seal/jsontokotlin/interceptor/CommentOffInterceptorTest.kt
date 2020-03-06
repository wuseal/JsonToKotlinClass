package wu.seal.jsontokotlin.interceptor

import com.winterbe.expekt.should
import org.junit.Test

import org.junit.Before
import wu.seal.jsontokotlin.generateKotlinDataClass
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.test.TestConfig

class CommentOffInterceptorTest {

    private val json = """{"a":123}"""


    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    @Test
    fun intercept() {

        json.generateKotlinDataClass().properties.forEach {
            it.comment.should.be.equal("123")
        }

        (json.generateKotlinDataClass().applyInterceptors(listOf(CommentOffInterceptor)) as DataClass).properties.forEach {
           it.comment.should.be.empty
       }

    }
}