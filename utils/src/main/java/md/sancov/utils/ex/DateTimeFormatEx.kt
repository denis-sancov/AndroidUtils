package md.sancov.utils.ex

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

inline fun<reified T> DateTimeFormatter.tryParse(text: CharSequence): T? {
    return try {
        val accessor = parse(text)

        val result = when (T::class) {
            Instant::class -> Instant.from(accessor)
            LocalDate::class -> LocalDate.from(accessor)
            LocalDateTime::class -> LocalDateTime.from(accessor)
            else -> null
        }

        result as? T

    } catch (_: Throwable) {
        null
    }
}