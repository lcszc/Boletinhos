package app.boletinhos.theming

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import app.boletinhos.R.attr as Attrs

@ColorInt
internal fun Context.getThemeAwareColor(@AttrRes colorAttr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(colorAttr, typedValue, true)
    return typedValue.data
}

fun Context.createThemeAwareDrawable(@DrawableRes drawableRes: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, drawableRes)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP || drawable == null) return drawable

    val wrapped = DrawableCompat.wrap(drawable)
    DrawableCompat.setTint(wrapped, getThemeAwareColor(Attrs.colorOnSurface))

    return wrapped
}
