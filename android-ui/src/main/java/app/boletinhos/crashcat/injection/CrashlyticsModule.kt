package app.boletinhos.crashcat.injection

import com.google.firebase.crashlytics.FirebaseCrashlytics

@dagger.Module object CrashlyticsModule {
    @dagger.Provides @common.AppScope internal fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}
