package app.boletinhos.injection.preferences

import android.content.SharedPreferences
import app.boletinhos.injection.context.AppContextComponent
import dagger.Component

@PreferencesScope
@Component(
    modules = [PreferencesModule::class],
    dependencies = [AppContextComponent::class]
)
interface PreferencesComponent {
    fun preferences(): SharedPreferences

    @Component.Factory interface Factory {
        fun create(appContextComponent: AppContextComponent): PreferencesComponent
    }
}