package md.sancov.utils.format.number

import android.os.Build
import java.util.*

class NumberFormatCompat(locale: Locale = Locale.getDefault()): NumberFormatProvider {
    private val format: NumberFormatProvider by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IcuNumberFormat(locale)
        } else {
            JavaNumberFormat(locale)
        }
    }

    override var maximumFractionDigits: Int
        get() = format.maximumFractionDigits
        set(value) {
            format.maximumFractionDigits = value
        }

    override var minimumFractionDigits: Int
        get() = format.minimumFractionDigits
        set(value) {
            format.minimumFractionDigits = value
        }
    override var currency: Currency?
        get() = format.currency
        set(value) {
            format.currency = value
        }

    override fun format(amount: Number?): String {
        return format.format(amount)
    }

    override fun format(amount: Number?, currencyIsoCode: String): String {
        return format.format(amount, currencyIsoCode)
    }
}