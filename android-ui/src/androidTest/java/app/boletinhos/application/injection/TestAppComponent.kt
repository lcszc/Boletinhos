package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.summary.injection.SummaryServiceModule

@common.AppScope
@dagger.Component(modules = [
    TestModule::class,
    TestDatabaseModule::class,
    SummaryServiceModule::class
])
interface TestAppComponent : AppComponent {
    fun bla(): SummaryService

    @dagger.Component.Factory interface Factory {
        fun create(@dagger.BindsInstance app: Application): TestAppComponent
    }
}
