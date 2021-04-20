package md.sancov.utils.ex

import java.time.Instant
import java.time.temporal.ChronoUnit

val Instant.dropTime: Instant get() {
    return truncatedTo(ChronoUnit.DAYS)
}