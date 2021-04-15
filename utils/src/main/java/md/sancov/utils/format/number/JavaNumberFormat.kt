package md.sancov.utils.format.number

import java.text.NumberFormat
import java.util.*

class JavaNumberFormat(private val locale: Locale): NumberFormatProvider {
    private val formatter: NumberFormat by lazy {
        val format = NumberFormat.getCurrencyInstance(locale)

        format.maximumFractionDigits = 2
        format.minimumFractionDigits = 2

        format
    }

    override var maximumFractionDigits: Int
        get() = formatter.maximumFractionDigits
        set(value) {
            formatter.maximumFractionDigits = value
        }

    override var minimumFractionDigits: Int
        get() = formatter.minimumFractionDigits
        set(value) {
            formatter.minimumFractionDigits = value
        }

    override var currency: Currency?
        get() = formatter.currency
        set(value) {
            formatter.currency = value
        }

    override fun format(amount: Number?): String {
        return formatter.format(amount)
    }

    override fun format(amount: Number?, currencyIsoCode: String): String {
        formatter.currency = Currency.getInstance(currencyIsoCode)
        return formatter.format(amount)
    }
}