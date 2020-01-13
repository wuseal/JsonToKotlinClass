package wu.seal.jsontokotlin.server.controllers

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import wu.seal.jsontokotlin.library.JsonToKotlinBuilder
import wu.seal.jsontokotlin.server.exceptions.InvalidJsonInputException
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.server.routes.generate.GenerateResponse

@Controller
class GenerateController {

    @PostMapping("/api/generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun generate(@RequestBody request: GenerateRequest): GenerateResponse {

        if (true) {
            throw InvalidJsonInputException()
        }


        val json = JsonToKotlinBuilder().build(
                request.json,
                request.className)

        return GenerateResponse(
                GenerateResponse.Data(json),
                true,
                -1,
                "OK"
        )
    }
}
