package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.application.MainApplication
import app.boletinhos.bill.injection.BillServiceModule
import app.boletinhos.crashcat.injection.CrashlyticsModule
import app.boletinhos.database.injection.AppDatabaseModule
import app.boletinhos.main.MainActivity
import app.boletinhos.main.injection.ActivityRetainedComponent
import app.boletinhos.preferences.injection.UserPreferencesModule
import app.boletinhos.summary.injection.SummaryServiceModule
import common.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
        UserPreferencesModule::class,
        CrashlyticsModule::class,
        AppDatabaseModule::class,
        SummaryServiceModule::class,
        BillServiceModule::class
    ]
)
interface AppComponent {
    fun inject(app: MainApplication)
    fun inject(activity: MainActivity)

    fun activityRetainedComponentFactory(): ActivityRetainedComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
