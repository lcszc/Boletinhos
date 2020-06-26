package app.boletinhos.main.injection

import android.app.Activity
import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule

@dagger.Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityComponent {
    fun globalServicesFactory(): GlobalServicesFactory

    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(
            @dagger.BindsInstance
            @common.ActivityContext
            activity: Activity
        ): ActivityComponent
    }
}
