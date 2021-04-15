package md.sancov.utils.ex

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlin.math.roundToInt

fun ViewGroup.findViewUnderTouch(ev: MotionEvent): View {
    val x = ev.x.roundToInt()
    val y = ev.y.roundToInt()

    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (x > child.left && x < child.right && y > child.top && y < child.bottom) {
            return child
        }
    }
    return this
}