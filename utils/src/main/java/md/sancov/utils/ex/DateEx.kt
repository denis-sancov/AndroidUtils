package md.sancov.utils.ex

import java.util.*

val Date.dropTime: Date
    get() = Calendar.getInstance().let {
        it.time = this
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
        it.time
    }