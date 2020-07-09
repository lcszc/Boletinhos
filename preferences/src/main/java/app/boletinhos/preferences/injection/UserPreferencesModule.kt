package app.boletinhos.preferences.injection

import android.content.Context
import android.content.SharedPreferences
import common.AppScope

@dagger.Module
object UserPreferencesModule {
    private const val NAME = "app_prefs"

    @dagger.Provides
    @common.AppScope
    internal fun provideSharedPreferences(
        @common.AppContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
}
