package app.boletinhos.injection.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Component

@Component(modules = [CrashlyticsModule::class])
interface CrashlyticsComponent {
    fun crashlytics(): FirebaseCrashlytics

    @Component.Factory interface Factory {
        fun create(): CrashlyticsComponent
    }
}
