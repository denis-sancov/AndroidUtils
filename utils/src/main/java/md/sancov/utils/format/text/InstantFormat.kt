package md.sancov.utils.format.text

import android.content.Context
import java.text.DateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class InstantFormat(style: FormatStyle = FormatStyle.SHORT): TextFormat<Instant> {
    private val df = DateTimeFormatter.ofLocalizedDate(style)

    override fun resolveString(ctx: Context, value: Instant?): String? {
        return value?.let { df.format(it) }
    }

    override fun resolveValue(ctx: Context, value: String?): Instant? {
        return value?.let {
            Instant.from(df.parse(it))
        }
    }
}
