package wu.seal.jsontokotlin.statistics

import java.util.*

/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */


const val ACTION_START = "action_start"
const val ACTION_SUCCESS_COMPLETE = "action_sucess_complete"

data class StartAction(
        val uuid: String = UUID,
        val actionType: String = ACTION_START,
        val time: String = Date().time.toString()

)

data class SuccessCompleteAction(
        val uuid: String = UUID,
        val actionType: String = ACTION_SUCCESS_COMPLETE,
        val time: String = Date().time.toString()
)