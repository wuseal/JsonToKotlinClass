package wu.seal.jsontokotlin.server.routes.generate

import com.fasterxml.jackson.annotation.JsonProperty


data class GenerateResponse(
        @JsonProperty("data")
        val `data`: Data,
        @JsonProperty("error")
        val error: Boolean, // false
        @JsonProperty("error_code")
        val errorCode: Int, // 123
        @JsonProperty("message")
        val message: String // Generated
)

data class Data(
        @JsonProperty("code")
        val code: String // The code goes here
)