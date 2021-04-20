package md.sancov.utils.ex

import java.time.Instant
import java.time.format.DateTimeFormatter


fun DateTimeFormatter.tryParse(text: CharSequence): Instant? {
    return try{
        Instant.from(parse(text))
    } catch (_: Throwable) {
        null
    }
}