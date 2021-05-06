package wu.seal.jsontokotlin

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * Created by Rody66 in 2019-04-18 3:16
 *
 * @author Rody66
 */
//https://json-schema.org/understanding-json-schema/reference/string.html#format
//TODO this map should be moved to ConfigManager (UI)
val JSON_SCHEMA_FORMAT_MAPPINGS = mapOf(
    "date-time" to OffsetDateTime::class.java.canonicalName,
    "date" to LocalDate::class.java.canonicalName,
    "time" to LocalTime::class.java.canonicalName,
    "decimal" to BigDecimal::class.java.canonicalName

    //here can be another formats
)
