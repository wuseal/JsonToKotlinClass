package wu.seal.jsontokotlinclass.server.models.routes.generate

import com.fasterxml.jackson.annotation.JsonProperty
import wu.seal.jsontokotlinclass.server.models.routes.base.BaseResponse


class GenerateResponse(data: Data, error: Boolean, errorCode: Int, message: String) :
        BaseResponse<GenerateResponse.Data>(data, error, errorCode, message) {
    data class Data(
            @field:JsonProperty("code")
            val code: String // The code goes here
    )
}

