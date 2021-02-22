package app.boletinhos.ext.view

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup

inline fun <reified T> View.service(serviceTag: String = T::class.java.name): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        return@lazy backstack.lookup<T>(serviceTag)
    }
}

val View.inflater: LayoutInflater get() = LayoutInflater.from(context)

fun View.getString(@StringRes resId: Int): String {
    return context.getString(resId)
}

fun View.getString(@StringRes resId: Int, vararg value: Any): String {
    return context.getString(resId, *value)
}
