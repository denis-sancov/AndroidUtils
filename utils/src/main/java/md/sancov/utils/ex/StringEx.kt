package md.sancov.utils.ex

import java.util.*

val String.isBool: Boolean
    get() {
        val tmp = lowercase(Locale.ROOT)
        return tmp == "false" || tmp == "true"
    }

fun String.parseOtpCode(): String {
    val option = RegexOption.IGNORE_CASE
    return Regex("(?<=codul\\s)[a-zA-Z0-9]{6,10}+", option).find(this)?.value ?: ""
}

fun String.insert(separator: String, n: Short): String {
    val builder = StringBuilder(length + separator.length * (length / n) + 1)

    var index = 0
    var prefix = ""

    while (index < length) {
        builder.append(prefix)

        prefix = separator

        builder.append(
            substring(index, kotlin.math.min(index + n, length))
        )

        index += n
    }
    return builder.toString()
}