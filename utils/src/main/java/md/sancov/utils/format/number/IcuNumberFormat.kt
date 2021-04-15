package md.sancov.utils.format.number

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class IcuNumberFormat(locale: Locale): NumberFormatProvider {
    private val formatter: NumberFormat by lazy {
        val format = NumberFormat.getInstance(
            locale,
            NumberFormat.ISOCURRENCYSTYLE
        )

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

    override var currency: java.util.Currency?
        get() {
            val code = formatter.currency.currencyCode
            return java.util.Currency.getInstance(code)
        }
        set(value) {
            formatter.currency = Currency.getInstance(value?.currencyCode)
        }

    override fun format(amount: Number?): String {
        return formatter.format(amount)
    }

    override fun format(amount: Number?, currencyIsoCode: String): String {
        formatter.currency = Currency.getInstance(currencyIsoCode)
        return formatter.format(amount)
    }
}