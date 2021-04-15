package md.sancov.utils.format

import android.content.Context
import md.sancov.utils.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.round

class DistanceFormat(private val ctx: Context) {
    private val locale: Locale = Locale.getDefault()

    private val decimalFormat: DecimalFormat by lazy {
        val formatter = NumberFormat.getNumberInstance(locale) as DecimalFormat
        formatter.applyPattern("#.#")
        formatter
    }

    fun format(meters: Double): String {
        val result = when {
            meters >= 1000 -> {
                Result(formatInKilometers(meters), Unit.Km)
            }
            else -> {
                Result(formatOverTenMeters(meters), Unit.Meter)
            }
        }

        val resources = ctx.resources

        return "${result.data} ${resources.getString(result.unit.stringId())}"
    }

    private fun formatOverTenMeters(distanceInMeters: Double): String {
        val number = round(distanceInMeters / 10.0) * 10
        val value: String = decimalFormat.format(number)
        return String.format(locale, "%s", value)
    }

    private fun formatInKilometers(distanceInMeters: Double): String {
        val value: String = decimalFormat.format(distanceInMeters / 1000)
        return String.format(locale, "%s", value)
    }

    data class Result(val data: String, val unit: Unit)

    enum class Unit {
        Meter,
        Km;

        fun stringId() = when (this) {
            Meter -> R.string.unit_meter
            Km -> R.string.unit_km
        }
    }
}
