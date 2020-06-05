package app.boletinhos.main

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
    }
}