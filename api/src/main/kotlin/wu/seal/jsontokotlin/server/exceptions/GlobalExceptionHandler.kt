package wu.seal.jsontokotlin.server.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import wu.seal.jsontokotlin.server.routes.base.BaseResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseJsonToKotlinException::class)
    @ResponseBody
    fun handleException(e: BaseJsonToKotlinException): ResponseEntity<Any> {
        return ResponseEntity<Any>(BaseResponse(null, true, e.errorCode, e.message!!), HttpStatus.OK)
    }
}