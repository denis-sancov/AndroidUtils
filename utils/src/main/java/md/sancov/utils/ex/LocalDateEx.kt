package md.sancov.utils.ex

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.tryParse(text: CharSequence, pattern: String): LocalDate? {
    return try {
        LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern))
    } catch (_: Throwable) {
        null
    }
}