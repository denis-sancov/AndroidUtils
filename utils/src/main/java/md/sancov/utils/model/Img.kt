package md.sancov.utils.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Img(@DrawableRes val resourceId: Int): Parcelable