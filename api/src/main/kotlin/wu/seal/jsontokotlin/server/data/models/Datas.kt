package wu.seal.jsontokotlin.server.data.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import wu.seal.jsontokotlin.server.utils.GenerateNoArgsConstruct
import javax.persistence.*

/**
 * 数据类
 * Created by Seal.Wu on 2017/9/26.
 */

@Entity(name = "ACTION_INFO")
@Table(name = "ACTION_INFO")
@GenerateNoArgsConstruct
data class ActionInfo @JsonCreator constructor(
        @field:JsonProperty("id")
        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        @field:JsonProperty("uuid")
        @Column(name = "UUID")
        val uuid: String,
        @field:JsonProperty("pluginVersion")
        @Column(name = "PLUGIN_VERSION")
        val pluginVersion: String,
        @field:JsonProperty("actionType")
        @Column(name = "ACTION_NAME")
        val actionType: String,
        @field:JsonProperty("time")
        @Column(name = "TIME_STAMP")
        val time: String,
        @field:JsonProperty("daytime")
        @Column(name = "DAY_TIME")
        val daytime: String
)


@Entity(name = "CONFIG_INFO")
@GenerateNoArgsConstruct
data class ConfigInfo(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @field:JsonProperty("id")
        val id: Int,
        @field:JsonProperty("uuid") val uuid: String,
        @field:JsonProperty("pluginVersion") val pluginVersion: String,
        @field:JsonProperty("isPropertiesVar") val isPropertiesVar: Boolean,
        @field:JsonProperty("isCommentOff") val isCommentOff: Boolean,
        @field:JsonProperty("isPropertyNullable") val isPropertyNullable: Boolean,
        @field:JsonProperty("initWithDefaultValue") val initWithDefaultValue: Boolean,
        @field:JsonProperty("targetJsonConverterLib") val targetJsonConverterLib: String,
        @field:JsonProperty("isInnerClassMode") val isInnerClassMode: Boolean,
        @field:JsonProperty("timeStamp") val timeStamp: String,
        @field:JsonProperty("daytime") val daytime: String

)


//
//fun main(args: Array<String>) {
//    val actionInfo = ActionInfo(uuid = "214fdsafsafafsdf", actionType = "start", time = "1234231434124324", id = 0, pluginVersion = "1.2.1", daytime = "2017-09-27")
//    val objectMapper = ObjectMapper()
//    val message = objectMapper.writeValueAsString(actionInfo)
//    println(message)
//    println(objectMapper.readValue<ActionInfo>(message, ActionInfo::class.java))
//}