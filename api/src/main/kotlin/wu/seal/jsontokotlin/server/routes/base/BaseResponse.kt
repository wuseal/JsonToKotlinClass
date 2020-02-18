package wu.seal.jsontokotlin.server.routes.base

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseResponse<T>(
        @field:JsonProperty("data")
        val `data`: T?,
        @field:JsonProperty("error")
        val error: Boolean, // false
        @field:JsonProperty("error_code")
        val errorCode: Int, // 123
        @field:JsonProperty("message")
        val message: String // Generated
)