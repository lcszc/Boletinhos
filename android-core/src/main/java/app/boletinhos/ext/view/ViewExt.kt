package app.boletinhos.ext.view

import android.view.LayoutInflater
import android.view.View
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup

inline fun <reified T> View.service(serviceTag: String = T::class.java.name): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        return@lazy backstack.lookup<T>(serviceTag)
    }
}

val View.inflater: LayoutInflater get() = LayoutInflater.from(context)
