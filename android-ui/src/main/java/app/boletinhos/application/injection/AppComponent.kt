package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.crashcat.injection.CrashlyticsModule
import app.boletinhos.application.MainApplication
import app.boletinhos.preferences.injection.UserPreferencesModule
import dagger.BindsInstance
import dagger.Component

@common.AppScope
@Component(modules = [AppModule::class, UserPreferencesModule::class, CrashlyticsModule::class])
interface AppComponent {
    fun inject(app: MainApplication)

    @Component.Factory interface Factory {
        @BindsInstance fun create(app: Application): AppComponent
    }
}
