package wu.seal.jsontokotlin.regression

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test
import wu.seal.jsontokotlin.test.TestConfig

class Issue130Test {

    private val expected = """{
  "a": "1->2"
}"""

    /**
     * init test environment before test
     */
    @Before
    fun setUp() {
        TestConfig.setToTestInitState()
    }

    /**
     * test issue #130 of Github Project issue
     */
    @Test
    fun testIssue130() {
        val prettyGson: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create()
        val jsonElement = prettyGson.fromJson<JsonElement>("""{ "a" : "1->2"}""", JsonElement::class.java)
        prettyGson.toJson(jsonElement).should.be.equal(expected)
    }
}