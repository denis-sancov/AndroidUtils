package md.sancov.utils.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class Text: Parcelable {
    @Parcelize
    data class Resource(@StringRes val resourceId: Int, val multiline: Boolean = false): Text()

    @Parcelize
    data class Chars(val sequence: CharSequence, val multiline: Boolean = false): Text()

    val isMultiline: Boolean get() = when (this) {
        is Resource -> multiline
        is Chars -> multiline
    }

    fun resolve(ctx: Context): String? {
        return when (this) {
            is Resource -> if (resourceId == 0) null else ctx.getString(resourceId)
            is Chars -> sequence.toString()
        }
    }
}