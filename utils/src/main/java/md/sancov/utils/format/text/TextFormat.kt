package md.sancov.utils.format.text

import android.content.Context

interface TextFormat<T> {
    fun resolveString(ctx: Context, value: T?): String?
    fun resolveValue(ctx: Context, value: String?): T?
}