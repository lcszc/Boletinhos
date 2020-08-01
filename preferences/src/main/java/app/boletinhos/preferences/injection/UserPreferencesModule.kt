package app.boletinhos.preferences.injection

import android.content.Context
import android.content.SharedPreferences
import common.AppContext
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object UserPreferencesModule {
    private const val NAME = "app_prefs"

    @Provides
    @AppScope
    internal fun provideSharedPreferences(
        @AppContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
}
