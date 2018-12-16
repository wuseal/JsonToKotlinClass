package wu.seal.jsontokotlin.ui

import com.intellij.openapi.ui.InputValidator
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by kezhenxu at 2018/12/15 16:58
 *
 * @author kezhenxu (kezhenxu94 at 163 dot com)
 */
object UrlInputValidator : InputValidator {
    override fun checkInput(inputString: String): Boolean = try {
        URL(inputString)
        true
    } catch (e: MalformedURLException) {
        false
    }

    override fun canClose(inputString: String): Boolean = true
}
