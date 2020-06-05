package app.boletinhos.injection.app

import app.boletinhos.injection.context.AppContextComponent
import app.boletinhos.injection.crashlytics.CrashlyticsComponent
import app.boletinhos.injection.preferences.PreferencesComponent
import app.boletinhos.main.MainApplication
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(dependencies = [PreferencesComponent::class, CrashlyticsComponent::class])
interface AppComponent {
    fun inject(app: MainApplication)

    fun appContextComponent(): AppContextComponent
    fun preferencesComponent(): PreferencesComponent
    fun crashlyticsComponent(): CrashlyticsComponent

    @Component.Factory interface Factory {
        fun create(
            @BindsInstance appContextComponent: AppContextComponent,
            preferencesComponent: PreferencesComponent,
            crashlyticsComponent: CrashlyticsComponent
        ): AppComponent
    }
}