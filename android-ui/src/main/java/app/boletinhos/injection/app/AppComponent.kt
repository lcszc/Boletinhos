package app.boletinhos.injection.app

import android.app.Application
import app.boletinhos.crashcat.injection.CrashlyticsModule
import app.boletinhos.main.MainApplication
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
