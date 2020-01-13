package wu.seal.jsontokotlin.server.exceptions

import javassist.tools.web.BadHttpRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import wu.seal.jsontokotlin.server.routes.base.BaseResponse
import java.lang.Exception

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseJsonToKotlinException::class)
    @ResponseBody
    fun handleException(e: BaseJsonToKotlinException): BaseResponse<Nothing> {
        return BaseResponse(null, true, e.errorCode, e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleBadHttpRequest(e: HttpMessageNotReadableException): BaseResponse<Nothing> {
        return BaseResponse(null, true, 1000, e.message!!)
    }
}