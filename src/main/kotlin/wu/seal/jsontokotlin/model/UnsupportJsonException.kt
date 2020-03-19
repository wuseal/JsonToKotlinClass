package wu.seal.jsontokotlin.model


/**
 * Throw out when the json to be convert don't support by this plugin or no need to convert to any classes
 */
class UnSupportJsonException(message: String = "") : Exception(message) {
    var advice: String = ""
    var adviceType: String = ""
}