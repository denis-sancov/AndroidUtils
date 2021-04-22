package md.sancov.utils.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

sealed class Img: Parcelable {
    @Parcelize
    data class Resource(@DrawableRes val resourceId: Int): Img()

    @Parcelize
    object Placeholder: Img()
    
    val isPlaceholder: Boolean get() {
        return this is Placeholder
    }
}