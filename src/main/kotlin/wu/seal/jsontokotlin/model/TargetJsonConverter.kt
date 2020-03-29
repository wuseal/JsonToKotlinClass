package wu.seal.jsontokotlin.model

/**
 *
 * Created by Seal.Wu on 2018/2/7.
 */
/**
 * This means which Json convert library you are using in you project
 */
enum class TargetJsonConverter {
    None, NoneWithCamelCase, Gson, FastJson, Jackson, MoShi, LoganSquare, Custom, MoshiCodeGen,Serializable
}
