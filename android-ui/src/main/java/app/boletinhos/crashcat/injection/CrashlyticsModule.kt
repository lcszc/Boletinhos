package app.boletinhos.crashcat.injection

import com.google.firebase.crashlytics.FirebaseCrashlytics
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object CrashlyticsModule {
    @Provides
    @AppScope
    internal fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}
