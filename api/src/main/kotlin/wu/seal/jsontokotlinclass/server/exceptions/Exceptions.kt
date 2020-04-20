package wu.seal.jsontokotlinclass.server.exceptions

open class BaseJsonToKotlinException(message: String, val errorCode: Int) : RuntimeException(message) {
    companion object {
        const val ERROR_CODE_INVALID_JSON_INPUT: Int = 1
    }
}

