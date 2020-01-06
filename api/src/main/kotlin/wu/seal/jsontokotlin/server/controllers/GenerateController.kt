package wu.seal.jsontokotlin.server.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import wu.seal.jsontokotlin.server.routes.generate.Data
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.server.routes.generate.GenerateResponse

@Controller()
class GenerateController {

    @PostMapping("/api/generate")
    @ResponseBody
    fun generate(@RequestBody request: GenerateRequest): GenerateResponse {
        return GenerateResponse(
                Data("This is code"),
                true,
                123,
                "Sample message"
        )
    }
}
