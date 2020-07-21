package app.boletinhos.application

import app.boletinhos.application.injection.AppComponent
import app.boletinhos.application.injection.DaggerTestAppComponent

class TestApplication : MainApplication() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.factory()
            .create(this)
    }

    override fun appComponent(): AppComponent {
        return appComponent
    }
}
