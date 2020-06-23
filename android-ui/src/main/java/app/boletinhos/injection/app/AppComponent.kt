package app.boletinhos.injection.app

import app.boletinhos.injection.context.AppContextComponent
import app.boletinhos.injection.crashlytics.CrashlyticsComponent
import app.boletinhos.injection.preferences.PreferencesComponent
import app.boletinhos.main.MainApplication
import app.boletinhos.preferences.injection.UserPreferencesModule
import dagger.BindsInstance
import dagger.Component

@common.AppScope
@Component(modules = [UserPreferencesModule::class], dependencies = [CrashlyticsComponent::class])
interface AppComponent {
    fun inject(app: MainApplication)

    fun appContextComponent(): AppContextComponent
    fun crashlyticsComponent(): CrashlyticsComponent

    @Component.Factory interface Factory {
        fun create(
            @BindsInstance appContextComponent: AppContextComponent,
            crashlyticsComponent: CrashlyticsComponent
        ): AppComponent
    }
}
