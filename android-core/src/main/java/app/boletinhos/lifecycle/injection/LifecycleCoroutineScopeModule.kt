package app.boletinhos.lifecycle.injection

import android.app.Activity
import app.boletinhos.lifecycle.IllegalInitializationException
import app.boletinhos.lifecycle.LifecycleCoroutineScope
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.navigatorktx.backstack
import com.zhuinden.simplestackextensions.servicesktx.canFind
import com.zhuinden.simplestackextensions.servicesktx.lookup
import common.CoroutineScopeContainer

@dagger.Module
object LifecycleCoroutineScopeModule {
    @dagger.Provides
    internal fun provideCoroutineScope(
        @common.ActivityContext activity: Activity,
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ): CoroutineScopeContainer {
        if (!Navigator.isNavigatorAvailable(activity)) {
            throw IllegalInitializationException
        }

        val backstack = activity.backstack

        if (backstack.canFind<LifecycleCoroutineScope>()) {
            return backstack.lookup()
        }

        return lifecycleCoroutineScope
    }
}
