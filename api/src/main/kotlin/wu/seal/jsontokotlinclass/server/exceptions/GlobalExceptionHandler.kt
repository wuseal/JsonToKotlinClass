package wu.seal.jsontokotlinclass.server.exceptions

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import wu.seal.jsontokotlinclass.server.models.routes.base.BaseResponse

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
    fun handleBadHttpRequest(e: Exception): BaseResponse<Nothing> {
        return BaseResponse(null, true, 1000, e.message!!)
    }
}