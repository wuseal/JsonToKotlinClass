package wu.seal.jsontokotlin

/**
 * Created by kezhenxu94 in 2019-03-28 22:21
 *
 * @author kezhenxu94 (kezhenxu94 at 163 dot com)
 */
val JSON_SCHEMA_TYPE_MAPPINGS = mapOf(
    "object" to Any::class,
    "array" to Array<Any>::class,
    "string" to String::class,
    "integer" to Int::class,
    "number" to Double::class,
    "boolean" to Boolean::class,
    "enum" to Enum::class,

    //See: https://json-schema.org/understanding-json-schema/reference/null.html
    "null" to Any::class
)
