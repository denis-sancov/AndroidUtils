package md.sancov.utils.ex

import android.content.Context
import md.sancov.utils.R
import java.util.*

fun Currency.resolveSymbol(): String = when (currencyCode) {
    "MDL" -> "L"
    "RON" -> "L"
    "RUB" -> "₽"
    "UAH" -> "₴"
    "CHF" -> "₣"
    "USD" -> "$"
    "EUR" -> "€"
    "GBP" -> "£"
    else -> symbol
}

fun Currency.localizedName(ctx: Context): String {
    val code = when (currencyCode) {
        "MDL" -> R.string.currency_mdl
        "EUR" -> R.string.currency_eur
        "USD" -> R.string.currency_usd
        else -> null
    }

    return if (code != null) ctx.getString(code) else currencyCode
}