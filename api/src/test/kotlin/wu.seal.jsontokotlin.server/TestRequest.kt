package wu.seal.jsontokotlin.server

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * 测试请求
 * Created by Seal.Wu on 2017/9/26.
 */

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TestRequest {

    val demeoActionInfo = """{"id":0,"uuid":"214fdsafsafafsdf","pluginVersion":"1.2.1","actionType":"start","time":"1234231434124324","daytime":"2017-09-27"}"""
    val demoConfigInfo = """{"uuid":"ff33e2b0-76b3-4107-b779-02a895f7ad7a","pluginVersion":"1.4","isPropertiesVar":false,"isCommentOff":false,"isPropertyNullable":true,"initWithDefaultValue":true,"targetJsonConverterLib":"None","isInnerClassMode":false,"timeStamp":"1506593711813","daytime":"2017-09-28"}"""

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun sendExceptionInfo() {
        mvc.perform(MockMvcRequestBuilders.post("/sendExceptionInfo").accept(MediaType.ALL).content("test exception info"))
                .andExpect(status().isOk())
    }

    fun testHomePage() {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hi, This is a Home Page!")))
    }

    @Test
    fun sendActionInfo() {
        mvc.perform(MockMvcRequestBuilders.post("/sendActionInfo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.ALL).content(demeoActionInfo))
                .andExpect(status().isOk)
    }

    @Test
    fun sendConfigInfo() {
        mvc.perform(MockMvcRequestBuilders.post("/sendConfigInfo").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.ALL).content(demoConfigInfo))
                .andExpect(status().isOk)
    }
}

val s = "select appkey as appkey, sdk_ver as sdk_ver, uv as uv, duration as duration ,rank as rank from (select appkey, sdk_ver, uv , duration, daytime,\n       row_number() over(partition by daytime,appkey order by daytime,uv desc) as rank\nfrom (\n    select ta.appkey, ta.sdk_ver, \n           count(*) as uv, \n           avg(tb.active_total_duration) as duration,daytime\n    from (\n        select appkey, uid, max(sdk_ver) as sdk_ver,daytime\n        from newmut.mresearch_client\n        where pid=2 and daytime between '2018-01-13' and '2018-02-22'\n        group by appkey, uid,daytime\n    ) as ta\n    join (\n        select appkey, uid, sum(active_total_duration) as active_total_duration,daytime\n        from newmut.mresearch_apps_run_state\n        where pid=2 and daytime between '2018-01-13' and '2018-02-22'\n        and ((appkey='UA-yiche-360001' and process_name='com.yiche.price:phone_monitor')\n            or (appkey='UA-youku-270001' and process_name='com.youku.phone:phone_monitor')\n            or (appkey='UA-kuwo-260001' and process_name='cn.kuwo.player:phone_monitor')\n            or (appkey='UA-mj-260001' and process_name='com.moji.mjweather:phone_monitor'))\n        and active_total_duration between 0 and 90\n        group by appkey, uid,daytime\n    ) as tb on tb.uid=ta.uid and tb.appkey=ta.appkey and tb.daytime=ta.daytime\n    group by ta.sdk_ver, ta.appkey,ta.daytime\n) as d\norder by daytime,appkey, uv desc) e where e.rank < 4"