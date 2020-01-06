package wu.seal.jsontokotlin.server.controllers

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.server.routes.generate.GenerateResponse

@Controller
class GenerateController {

    @PostMapping("/api/generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun generate(@RequestBody request: GenerateRequest): GenerateResponse {
        return GenerateResponse(
                GenerateResponse.Data("some code goes here"),
                true,
                -1,
                "OK"
        )
    }
}
