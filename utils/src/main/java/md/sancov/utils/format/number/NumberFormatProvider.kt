package md.sancov.utils.format.number

import java.util.*

interface NumberFormatProvider {
    var maximumFractionDigits: Int
    var minimumFractionDigits: Int

    var currency: Currency?

    fun format(amount: Number?): String

    fun format(amount: Number?, currencyIsoCode: String): String

    fun format(amount: Number?, currency: Currency): String {
        return format(amount, currency.currencyCode)
    }
}