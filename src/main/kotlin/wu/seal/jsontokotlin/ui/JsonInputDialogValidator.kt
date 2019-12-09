package wu.seal.jsontokotlin.ui

import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.InputValidator


class JsonInputDialogValidator : InputValidator {
    var jsonInputEditor: Editor? = null

    override fun checkInput(className: String): Boolean {
        if (jsonInputEditor == null) return false
        return className.isNotBlank() && inputIsValidJson(jsonInputEditor!!.document.text)
    }

    override fun canClose(inputString: String): Boolean = true

    private fun inputIsValidJson(string: String) = try {
        val jsonElement = JsonParser().parse(string)
        (jsonElement.isJsonObject || jsonElement.isJsonArray)
    } catch (e: JsonSyntaxException) {
        false
    }
}