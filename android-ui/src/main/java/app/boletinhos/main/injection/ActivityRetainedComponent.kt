package app.boletinhos.main.injection

import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule
import app.boletinhos.summary.SummaryViewModel
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.lookup
import dagger.BindsInstance

@common.ActivityRetainedScope
@dagger.Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityRetainedComponent {
    fun coroutineScope(): LifecycleAwareCoroutineScope

    fun summaryViewModel(): SummaryViewModel

    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance backstack: Backstack): ActivityRetainedComponent
    }
}

val Backstack.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
val ServiceBinder.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
