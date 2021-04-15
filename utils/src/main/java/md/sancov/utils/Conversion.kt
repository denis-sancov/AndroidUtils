package md.sancov.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

private val metrics: DisplayMetrics get() = Resources.getSystem().displayMetrics

val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()

val Int.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), metrics)