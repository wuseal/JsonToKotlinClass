package wu.seal.jsontokotlin.model.builder

/**
 * code builder global config
 *
 * Created by Nstd on 2020/6/30 18:40.
 */
class CodeBuilderConfig private constructor() {

    var configMap = mutableMapOf<String, Any>()

    companion object {
        val instance: CodeBuilderConfig by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CodeBuilderConfig()
        }
    }

    fun setConfig(key: String, value: Any) {
        configMap[key] = value
    }

    fun <T> getConfig(key: String, default: T): T {
        var result = default
        if(configMap.containsKey(key)) {
            result = configMap[key] as T
        }
        return result
    }

    fun removeConfig(key: String) {
        if(configMap.containsKey(key)) {
            configMap.remove(key)
        }
    }
}