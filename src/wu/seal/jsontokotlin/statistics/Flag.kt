package wu.seal.jsontokotlin.statistics

import wu.seal.jsontokotlin.ConfigManager

/**
 * Flag relative
 * Created by Seal.Wu on 2017/9/25.
 */


val UUID = if (ConfigManager.userUUID.isEmpty()) {
    val uuid = java.util.UUID.randomUUID().toString()
    ConfigManager.userUUID = uuid
    uuid
} else ConfigManager.userUUID