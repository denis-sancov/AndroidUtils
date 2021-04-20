package md.sancov.utils.format.text

import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.text.method.KeyListener
import md.sancov.utils.filter.InputFilterDecimal
import java.text.DecimalFormatSymbols

interface TextContent {
    val inputType: Int
    val filters: Array<InputFilter>
    val maxLines: Int get() = 1
    val keyListener: KeyListener? get() = null

    companion object {
        val numeric = object : TextContent {
            override val inputType: Int = InputType.TYPE_CLASS_NUMBER or
                    InputType.TYPE_NUMBER_FLAG_DECIMAL or
                    InputType.TYPE_NUMBER_FLAG_SIGNED

            override val filters: Array<InputFilter> = arrayOf(
                InputFilter.LengthFilter(16),
                InputFilterDecimal(2),
            )

            override val keyListener: KeyListener
                get() {
                val separator = DecimalFormatSymbols.getInstance().decimalSeparator
                return DigitsKeyListener.getInstance("0123456789$separator")
            }
        }

        val iban = object : TextContent {
            override val inputType: Int = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

            override val filters: Array<InputFilter> = arrayOf(
                InputFilter { s, _, _, _, _, _ ->
                    s.replace(Regex("[^A-Za-z0-9]"), "")
                },
                InputFilter.LengthFilter(24),
                InputFilter.AllCaps()
            )
        }

        val resident = object : TextContent {
            override val inputType: Int = InputType.TYPE_CLASS_NUMBER

            override val filters: Array<InputFilter> = arrayOf(
                InputFilter { s, _, _, _, _, _ ->
                    s.replace(Regex("[^0-9]"), "")
                },
                InputFilter.LengthFilter(13),
            )
        }

        val nonResident = object : TextContent {
            override val inputType: Int = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            override val filters: Array<InputFilter> = arrayOf(
                InputFilter { s, _, _, _, _, _ ->
                    s.replace(Regex("[^A-Za-z0-9]"), "")
                },
                InputFilter.LengthFilter(20),
            )
        }

        val raw get() = raw()

        fun raw(length: Int = 60, maxLines: Int = 1) = object : TextContent {
            override val inputType: Int = if (maxLines > 1) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            } else {
                InputType.TYPE_CLASS_TEXT
            }

            override val filters: Array<InputFilter> = arrayOf(InputFilter.LengthFilter(length))
            override val maxLines: Int = maxLines
        }
    }
}