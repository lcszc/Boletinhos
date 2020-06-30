package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.application.MainApplication
import app.boletinhos.crashcat.injection.CrashlyticsModule
import app.boletinhos.main.MainActivity
import app.boletinhos.main.injection.ActivityRetainedComponent
import app.boletinhos.preferences.injection.UserPreferencesModule

@common.AppScope
@dagger.Component(
    modules = [
        AppModule::class,
        UserPreferencesModule::class,
        CrashlyticsModule::class
    ]
)
interface AppComponent {
    fun inject(app: MainApplication)
    fun inject(activity: MainActivity)

    fun activityRetainedComponentFactory(): ActivityRetainedComponent.Factory

    @dagger.Component.Factory interface Factory {
        fun create(@dagger.BindsInstance app: Application): AppComponent
    }
}
