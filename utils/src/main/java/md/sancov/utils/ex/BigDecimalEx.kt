package md.fincombank.utils.ex

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.times(value: BigDecimal): BigDecimal {
    return multiply(value)
}

fun BigDecimal.safeDiv(value: Double): BigDecimal {
    return divide(BigDecimal(value), 10, RoundingMode.HALF_EVEN)
}

fun BigDecimal.safeDiv(value: BigDecimal): BigDecimal {
    return divide(value, 10, RoundingMode.HALF_EVEN)
}