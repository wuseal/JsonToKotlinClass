package wu.seal.jsontokotlin.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.winterbe.expekt.should
import org.junit.Test

class TestJsonElement {

    private val gson = Gson()
    @Test
    fun isStringJsonObject() {
        val jsonElement = gson.toJsonTree("hello")
        jsonElement.isJsonObject.should.be.`false`
    }

    @Test
    fun isObjectStringPropertyIsJsonObject() {
        val json = """{a:"hello"}"""
        val jsonObject = gson.fromJson<JsonElement>(json, JsonElement::class.java)
        jsonObject.asJsonObject.get("a").isJsonObject.should.be.`false`
    }

    @Test
    fun isJsonStringArrayElementIsJsonObject() {
        val json = """["hello","yes"]"""
        val jsonObject = gson.fromJson<JsonElement>(json, JsonElement::class.java)
        jsonObject.asJsonArray[0].isJsonObject.should.be.`false`
    }
}