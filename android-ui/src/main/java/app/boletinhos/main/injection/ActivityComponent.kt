package app.boletinhos.main.injection

import android.app.Activity
import app.boletinhos.lifecycle.injection.LifecycleCoroutineScopeModule

@dagger.Subcomponent(modules = [LifecycleCoroutineScopeModule::class])
interface ActivityComponent {
    @dagger.Subcomponent.Factory interface Factory {
        fun create(
            @dagger.BindsInstance
            @common.ActivityContext
            activity: Activity
        ): ActivityComponent
    }
}
