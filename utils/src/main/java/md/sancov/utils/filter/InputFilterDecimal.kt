package md.sancov.utils.filter

import android.text.InputFilter
import android.text.Spanned
import java.text.DecimalFormatSymbols

data class InputFilterDecimal(val decimalDigits: Int) : InputFilter {
    private val separator = DecimalFormatSymbols.getInstance().decimalSeparator

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence? {
        var dotPos = -1
        val len: Int = dest.length

        for (i in dest.indices) {
            val c = dest[i]
            if (c == '.' || c == ',') {
                dotPos = i
                break
            }
        }

        if (dotPos >= 0) {
            if (source == "." || source == ",") {
                return ""
            }

            if (dend <= dotPos) {
                return null
            }

            if (len - dotPos > decimalDigits) {
                return ""
            }
        }
        return null
    }
}
