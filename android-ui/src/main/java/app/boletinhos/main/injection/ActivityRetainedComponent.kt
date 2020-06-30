package app.boletinhos.main.injection

import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup
import common.UiCoroutineScope
import kotlinx.coroutines.CoroutineScope

@common.ActivityRetainedScope
@dagger.Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityRetainedComponent {
    @UiCoroutineScope fun coroutineScope(): CoroutineScope

    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(): ActivityRetainedComponent
    }
}

val Backstack.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
