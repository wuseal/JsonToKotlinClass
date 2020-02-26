package wu.seal.jsontokotlin.feedback

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */

const val ACTION_START = "action_start"
const val ACTION_SUCCESS_COMPLETE = "action_success_complete"
const val ACTION_FORMAT_JSON = "action_format_json"
const val ACTION_CLICK_PROJECT_URL = "action_click_project_url"

data class StartAction(
    val uuid: String = UUID,
    val pluginVersion: String = PLUGIN_VERSION,
    val actionType: String = ACTION_START,
    val time: String = Date().time.toString(),
    val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
)

data class SuccessCompleteAction(
    val uuid: String = UUID,
    val pluginVersion: String = PLUGIN_VERSION,
    val actionType: String = ACTION_SUCCESS_COMPLETE,
    val time: String = Date().time.toString(),
    val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
)

data class FormatJSONAction(
    val uuid: String = UUID,
    val pluginVersion: String = PLUGIN_VERSION,
    val actionType: String = ACTION_FORMAT_JSON,
    val time: String = Date().time.toString(),
    val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
)

data class ClickProjectURLAction(
        val uuid: String = UUID,
        val pluginVersion: String = PLUGIN_VERSION,
        val actionType: String = ACTION_CLICK_PROJECT_URL,
        val time: String = Date().time.toString(),
        val daytime: String = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
)