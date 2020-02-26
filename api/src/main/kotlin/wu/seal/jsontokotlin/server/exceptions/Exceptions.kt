package wu.seal.jsontokotlin.server.exceptions

import java.lang.RuntimeException

open class BaseJsonToKotlinException(message: String, val errorCode: Int) : RuntimeException(message) {
    companion object {
        const val ERROR_CODE_INVALID_JSON_INPUT: Int = 1
    }
}

class InvalidJsonInputException(
) : BaseJsonToKotlinException("Invalid JSON input", ERROR_CODE_INVALID_JSON_INPUT)


