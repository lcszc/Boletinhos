package app.boletinhos.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class PreferencesModule {
    @Provides
    @Singleton
    fun appPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            UserPreferences.PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
}