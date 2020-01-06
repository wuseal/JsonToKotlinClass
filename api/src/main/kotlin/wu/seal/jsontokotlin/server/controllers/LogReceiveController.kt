package wu.seal.jsontokotlin.server.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wu.seal.jsontokotlin.server.data.models.ActionInfo
import wu.seal.jsontokotlin.server.data.repositories.ActionRepository
import wu.seal.jsontokotlin.server.data.models.ConfigInfo
import wu.seal.jsontokotlin.server.data.repositories.ConfigInfoRepository
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.server.utils.writeExceptionLog

/**
 * 日志接收器
 * Created by Seal.Wu on 2017/9/26.
 */

@RestController
class LogReceiveController {

    @Autowired
    private lateinit var actionRepository: ActionRepository

    @Autowired
    private lateinit var configInfoRepository: ConfigInfoRepository

    @PostMapping("sendActionInfo", consumes = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun receiveActionInfo(@RequestBody actionInfo: ActionInfo) {
        actionRepository.save(actionInfo)

    }

    @PostMapping("sendExceptionInfo")
    fun receiveExceptionInfo(@RequestBody exceptionInfo: String) {
        writeExceptionLog(exceptionInfo)

    }

    @PostMapping("sendConfigInfo")
    fun receiveConfigInfo(@RequestBody configInfo: ConfigInfo) {
        configInfoRepository.save(configInfo)
    }

    @RequestMapping("/")
    fun home(): String {
        return "Hi, This is a Home Page!"
    }

    @PostMapping("/generate")
    fun generate(@RequestBody request: GenerateRequest): String {
        return "This page generate JSON for you! ${request.annotationLib}"
    }


}