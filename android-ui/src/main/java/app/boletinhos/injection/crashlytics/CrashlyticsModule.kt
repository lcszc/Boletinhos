package app.boletinhos.injection.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides

@Module
object CrashlyticsModule {
    @Provides fun crashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}
