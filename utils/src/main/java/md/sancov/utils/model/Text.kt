package md.sancov.utils.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class Text: Parcelable {
    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int

    @Parcelize
    data class Resource(@StringRes val resourceId: Int, val multiline: Boolean = false): Text()

    @Parcelize
    data class Chars(val sequence: CharSequence?, val multiline: Boolean = false): Text()

    @Parcelize
    data class Lambda(val lambda: (Context) -> CharSequence?): Text()

    val isMultiline: Boolean get() = when (this) {
        is Resource -> multiline
        is Chars -> multiline
        else -> false
    }

    val isEmpty: Boolean get() = when (this) {
        is Resource -> resourceId <= 0
        is Chars -> sequence.isNullOrBlank()
        else -> false
    }

    fun resolve(ctx: Context): CharSequence? {
        return when (this) {
            is Resource -> if (resourceId == 0) null else ctx.getString(resourceId)
            is Chars -> sequence
            is Lambda -> lambda(ctx)
            else -> null
        }
    }
}