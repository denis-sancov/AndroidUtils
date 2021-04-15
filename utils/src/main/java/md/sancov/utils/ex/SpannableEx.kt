package md.sancov.utils.ex

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.StringRes

data class SpanLink(val arg: String, val callback: () -> Unit)

fun spanWithLinks(ctx: Context, @StringRes template: Int, vararg actions: SpanLink): Spannable {
    val args = actions
        .map { it.arg }
        .toTypedArray()

    val buffer = SpannableString(ctx.resources.getString(template, *args))

    actions.forEach {
        val span = object: ClickableSpan() {
            override fun onClick(widget: View) {
                it.callback()
            }
        }

        val idx = buffer.indexOf(it.arg)

        buffer.setSpan(
            span,
            idx,
            idx + it.arg.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return buffer
}