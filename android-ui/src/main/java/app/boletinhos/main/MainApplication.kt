package app.boletinhos.main

import android.app.Application
import app.boletinhos.injection.context.DaggerAppContextComponent
import app.boletinhos.injection.app.AppComponent
import app.boletinhos.injection.app.DaggerAppComponent
import app.boletinhos.injection.context.AppContextComponent
import app.boletinhos.injection.crashlytics.CrashlyticsComponent
import app.boletinhos.injection.crashlytics.DaggerCrashlyticsComponent
import app.boletinhos.injection.preferences.DaggerPreferencesComponent
import app.boletinhos.injection.preferences.PreferencesComponent
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainApplication : Application() {
    private lateinit var component: AppComponent

    private fun contextComponent(): AppContextComponent {
        return DaggerAppContextComponent.factory()
            .create(this)
    }

    private fun preferencesComponent(): PreferencesComponent {
        return DaggerPreferencesComponent.factory()
            .create(contextComponent())
    }

    private fun crashlyticsComponent(): CrashlyticsComponent {
        return DaggerCrashlyticsComponent.factory().create()
    }

    private fun injector(): AppComponent {
        return DaggerAppComponent.factory()
            .create(contextComponent(), preferencesComponent(), crashlyticsComponent())
            .also { component -> component.inject(this) }
    }

    override fun onCreate() {
        super.onCreate()
        component = injector()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
    }

    fun mainComponent() = component
}