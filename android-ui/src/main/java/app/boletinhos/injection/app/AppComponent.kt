package app.boletinhos.injection.app

import app.boletinhos.injection.context.AppContextComponent
import app.boletinhos.injection.crashlytics.CrashlyticsModule
import app.boletinhos.main.MainApplication
import app.boletinhos.preferences.injection.UserPreferencesModule
import dagger.BindsInstance
import dagger.Component

@common.AppScope
@Component(modules = [UserPreferencesModule::class, CrashlyticsModule::class])
interface AppComponent {
    fun inject(app: MainApplication)

    fun appContextComponent(): AppContextComponent

    @Component.Factory interface Factory {
        fun create(
            @BindsInstance appContextComponent: AppContextComponent
        ): AppComponent
    }
}
