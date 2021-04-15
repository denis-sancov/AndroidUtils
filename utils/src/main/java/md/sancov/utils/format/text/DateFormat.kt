package md.sancov.utils.format.text

import android.content.Context
import java.text.DateFormat
import java.util.*

class DateFormat(style: Int = DateFormat.SHORT): TextFormat<Date> {
    private val df = DateFormat.getDateInstance(style)

    override fun resolveString(ctx: Context, value: Date?): String? {
        return value?.let { df.format(it) }
    }

    override fun resolveValue(ctx: Context, value: String?): Date? {
        return value?.let { df.parse(it) }
    }
}
