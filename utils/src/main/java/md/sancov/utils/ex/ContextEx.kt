package md.sancov.utils.ex

import android.content.Context
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


val Context.isUsingDarkMode: Boolean
    get() {
        val status = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return status == Configuration.UI_MODE_NIGHT_YES
    }

val Context.hasGoogleServices: Boolean
    get() {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)
        return result == ConnectionResult.SUCCESS
    }

@ColorInt
fun Context.loadAttrColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

@StyleRes
fun Context.loadTextAppearance(@AttrRes attrRes: Int): Int {
    val a: TypedArray = obtainStyledAttributes(intArrayOf(attrRes))
    return try {
        a.getResourceId(0, 0)
    } finally {
        a.recycle()
    }
}

fun Context.loadDrawable(@AttrRes attrRes: Int): Drawable? {
    val a: TypedArray = obtainStyledAttributes(intArrayOf(attrRes))
    return try {
        a.getDrawable(0)
    } finally {
        a.recycle()
    }
}

fun Context.bitmapFromVector(@DrawableRes id: Int): Bitmap? {
    return AppCompatResources.getDrawable(this, id)?.toBitmap()
}

fun Context.resolveIntDim(@DimenRes id: Int): Int {
    return resources.getDimension(id).toInt()
}