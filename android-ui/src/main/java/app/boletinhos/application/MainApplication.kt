package app.boletinhos.application

import android.app.Application
import app.boletinhos.crashcat.CrashCat
import app.boletinhos.application.injection.AppComponent
import app.boletinhos.application.injection.DaggerAppComponent
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
