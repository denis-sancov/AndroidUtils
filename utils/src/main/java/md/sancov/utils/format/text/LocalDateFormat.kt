package md.sancov.utils.format.text

import android.content.Context
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class LocalDateFormat(style: FormatStyle = FormatStyle.SHORT): TextFormat<LocalDate> {
    private val df = DateTimeFormatter
            .ofLocalizedDate(style)
            .withZone(ZoneId.systemDefault())

    override fun resolveString(ctx: Context, value: LocalDate?): String? {
        return value?.let { df.format(it) }
    }

    override fun resolveValue(ctx: Context, value: String?): LocalDate? {
        return value?.let {
            LocalDate.from(df.parse(it))
        }
    }
}
