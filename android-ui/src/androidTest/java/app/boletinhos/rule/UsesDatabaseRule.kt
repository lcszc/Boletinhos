package app.boletinhos.rule

import androidx.test.core.app.ApplicationProvider
import app.boletinhos.application.TestApplication
import app.boletinhos.application.injection.TestAppComponent
import app.boletinhos.database.AppDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import javax.inject.Inject

class UsesDatabaseRule : TestWatcher() {
    @Inject lateinit var database: AppDatabase

    override fun starting(description: Description?) {
        super.starting(description)
        val component = (ApplicationProvider.getApplicationContext<TestApplication>()
            .appComponent() as TestAppComponent)

        component.inject(this)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        database.clearAllTables()
    }
}