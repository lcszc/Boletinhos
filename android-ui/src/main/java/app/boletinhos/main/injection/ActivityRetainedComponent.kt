package app.boletinhos.main.injection

import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestackextensions.servicesktx.lookup
import common.UiCoroutineScope
import dagger.BindsInstance
import kotlinx.coroutines.CoroutineScope

@common.ActivityRetainedScope
@dagger.Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityRetainedComponent {
    @UiCoroutineScope fun coroutineScope(): CoroutineScope

    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance backstack: Backstack): ActivityRetainedComponent
    }
}

val Backstack.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
