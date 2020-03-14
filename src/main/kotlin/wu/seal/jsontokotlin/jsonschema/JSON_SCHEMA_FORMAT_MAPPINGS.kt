package wu.seal.jsontokotlin.jsonschema

import java.math.BigDecimal

/**
 * Created by rodion-m in 2019-04-18 3:16
 *
 * @author rodion-m
 */
//https://json-schema.org/understanding-json-schema/reference/string.html#format
//TODO this map should be moved to ConfigManager (UI)
val JSON_SCHEMA_FORMAT_MAPPINGS = mapOf(
        "date-time" to "org.threeten.bp.OffsetDateTime",
        "date" to "org.threeten.bp.LocalDate",
        "time" to "org.threeten.bp.LocalTime",
        "decimal" to BigDecimal::class.qualifiedName

        //here can be another formats
)
