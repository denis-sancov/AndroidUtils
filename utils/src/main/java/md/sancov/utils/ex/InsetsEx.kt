package md.sancov.utils.ex

import androidx.core.graphics.Insets

fun Insets.update(
        left: Int = this.left,
        top: Int = this.top,
        right: Int = this.right,
        bottom: Int = this.bottom
): Insets {
    return Insets.of(left, top, right, bottom)
}