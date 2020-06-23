package app.boletinhos.main

import android.app.Application
import app.boletinhos.crashcat.CrashCat
import app.boletinhos.injection.app.AppComponent
import app.boletinhos.injection.app.DaggerAppComponent
import javax.inject.Inject

class MainApplication : Application() {
    private lateinit var component: AppComponent

    private fun injector(): AppComponent {
        return DaggerAppComponent.factory()
            .create(this)
            .also { component -> component.inject(this) }
    }

    @Inject lateinit var crashCat: CrashCat

    override fun onCreate() {
        super.onCreate()
        component = injector()
        crashCat.configure()
    }

    fun mainComponent() = component
}
