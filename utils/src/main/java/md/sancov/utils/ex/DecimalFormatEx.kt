package md.sancov.utils.ex

import java.math.BigDecimal
import java.text.DecimalFormat

fun DecimalFormat.tryFormat(value: BigDecimal?): String? {
    return value?.let { format(it) }
}

fun DecimalFormat.tryParse(text: String): BigDecimal? {
    return try { parse(text) as BigDecimal? } catch (_: Throwable) { null }
}
