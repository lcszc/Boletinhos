package app.boletinhos.main.injection

import app.boletinhos.bill.add.AddBillViewModel
import app.boletinhos.lifecycle.LifecycleAwareCoroutineScope
import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule
import app.boletinhos.summary.SummaryViewModel
import app.boletinhos.welcome.WelcomeViewModel
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.servicesktx.lookup
import common.ActivityRetainedScope
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityRetainedScope
@Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityRetainedComponent {
    fun coroutineScope(): LifecycleAwareCoroutineScope

    fun summaryViewModel(): SummaryViewModel
    fun welcomeViewModel(): WelcomeViewModel
    fun addBillViewModel(): AddBillViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance backstack: Backstack): ActivityRetainedComponent
    }
}

val Backstack.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
val ServiceBinder.activityRetainedComponent get() = lookup<ActivityRetainedService>().component
