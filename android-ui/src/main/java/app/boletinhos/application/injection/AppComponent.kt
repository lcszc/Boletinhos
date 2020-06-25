package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.application.MainApplication
import app.boletinhos.crashcat.injection.CrashlyticsModule
import app.boletinhos.main.injection.ActivityComponent
import app.boletinhos.preferences.injection.UserPreferencesModule

@common.AppScope
@dagger.Component(
    modules = [
        AppModule::class,
        UserPreferencesModule::class,
        CrashlyticsModule::class,
        AppComponent.Subcomponent::class
    ]
)
interface AppComponent {
    fun activityComponentFactory(): ActivityComponent.Factory

    fun inject(app: MainApplication)

    @dagger.Component.Factory interface Factory {
        fun create(@dagger.BindsInstance app: Application): AppComponent
    }

    @dagger.Module(subcomponents = [ActivityComponent::class])
    interface Subcomponent
}
