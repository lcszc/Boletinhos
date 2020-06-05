package app.boletinhos.injection.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module object PreferencesModule {
    private const val NAME = "app_prefs"

    @Provides fun preferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
}