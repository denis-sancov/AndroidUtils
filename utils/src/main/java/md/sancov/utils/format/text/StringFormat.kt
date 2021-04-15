package md.sancov.utils.format.text

import android.content.Context

class StringFormat: TextFormat<String> {
    override fun resolveString(ctx: Context, value: String?): String? {
        return value
    }

    override fun resolveValue(ctx: Context, value: String?): String? {
        return value
    }
}
