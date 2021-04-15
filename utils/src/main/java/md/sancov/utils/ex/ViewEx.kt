package md.sancov.utils.ex

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes

fun View.setBackgroundAttrColor(@AttrRes attrRes: Int) {
    val color = context.loadAttrColor(attrRes)
    setBackgroundColor(color)
}

fun View.focusKeyboard() {
    val ctx = context ?: return

    requestFocus()

    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View?.resignKeyboard() {
    val ctx = this?.context ?: return

    val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)

    clearFocus()
}