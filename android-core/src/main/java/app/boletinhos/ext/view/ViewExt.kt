package app.boletinhos.ext.view

import android.view.View
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup

inline fun <reified T> View.service(serviceTag: String = T::class.java.name): T {
    return backstack.lookup(serviceTag)
}