package wu.seal.jsontokotlinclass.server.models.routes.addhit

import com.fasterxml.jackson.annotation.JsonProperty
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest

class AddHitRequest(
        @JsonProperty("client")
        val client: String,
        @JsonProperty("generate_request")
        val generateRequest: GenerateRequest
)