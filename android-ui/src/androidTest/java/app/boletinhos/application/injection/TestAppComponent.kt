package app.boletinhos.application.injection

import android.app.Application
import app.boletinhos.bill.injection.ManageBillServiceModule
import app.boletinhos.summary.SummaryViewTest
import app.boletinhos.summary.injection.SummaryServiceModule
import app.boletinhos.rule.UsesDatabaseRule
import common.AppScope
import dagger.Component

@AppScope
@Component(modules = [
    TestModule::class,
    TestDatabaseModule::class,
    SummaryServiceModule::class,
    ManageBillServiceModule::class
])
interface TestAppComponent : AppComponent {
    fun inject(test: SummaryViewTest)
    fun inject(rule: UsesDatabaseRule)

    @Component.Factory interface Factory {
        fun create(@dagger.BindsInstance app: Application): TestAppComponent
    }
}
