package app.boletinhos.preferences.injection

import android.content.Context
import android.content.SharedPreferences

@dagger.Module object UserPreferencesModule {
    private const val NAME = "app_prefs"

    @dagger.Provides internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
}
