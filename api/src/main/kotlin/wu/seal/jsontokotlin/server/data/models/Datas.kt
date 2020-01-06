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
        @JsonProperty("id")
        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        @JsonProperty("uuid")
        @Column(name = "UUID")
        val uuid: String,
        @JsonProperty("pluginVersion")
        @Column(name = "PLUGIN_VERSION")
        val pluginVersion: String,
        @JsonProperty("actionType")
        @Column(name = "ACTION_NAME")
        val actionType: String,
        @JsonProperty("time")
        @Column(name = "TIME_STAMP")
        val time: String,
        @JsonProperty("daytime")
        @Column(name = "DAY_TIME")
        val daytime: String
)


@Entity(name = "CONFIG_INFO")
@GenerateNoArgsConstruct
data class ConfigInfo(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @JsonProperty("id")
        val id: Int,
        @JsonProperty("uuid") val uuid: String,
        @JsonProperty("pluginVersion") val pluginVersion: String,
        @JsonProperty("isPropertiesVar") val isPropertiesVar: Boolean,
        @JsonProperty("isCommentOff") val isCommentOff: Boolean,
        @JsonProperty("isPropertyNullable") val isPropertyNullable: Boolean,
        @JsonProperty("initWithDefaultValue") val initWithDefaultValue: Boolean,
        @JsonProperty("targetJsonConverterLib") val targetJsonConverterLib: String,
        @JsonProperty("isInnerClassMode") val isInnerClassMode: Boolean,
        @JsonProperty("timeStamp") val timeStamp: String,
        @JsonProperty("daytime") val daytime: String

)


//
//fun main(args: Array<String>) {
//    val actionInfo = ActionInfo(uuid = "214fdsafsafafsdf", actionType = "start", time = "1234231434124324", id = 0, pluginVersion = "1.2.1", daytime = "2017-09-27")
//    val objectMapper = ObjectMapper()
//    val message = objectMapper.writeValueAsString(actionInfo)
//    println(message)
//    println(objectMapper.readValue<ActionInfo>(message, ActionInfo::class.java))
//}