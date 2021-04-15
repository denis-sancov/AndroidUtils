package md.sancov.utils.format.text

import android.content.Context
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class AmountFormat: TextFormat<Number> {
    private val nf: DecimalFormat by lazy {
        val separator = DecimalFormatSymbols.getInstance().decimalSeparator

        DecimalFormat("#$separator##").apply {
            isParseBigDecimal = true
            isDecimalSeparatorAlwaysShown = true
            isGroupingUsed = false
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    override fun resolveString(ctx: Context, value: Number?): String? {
        return value?.let { nf.format(it) }
    }

    override fun resolveValue(ctx: Context, value: String?): Number? {
        return value?.let {
            try { nf.parse(it) as BigDecimal? } catch (_: Throwable) { null }
        }
    }
}