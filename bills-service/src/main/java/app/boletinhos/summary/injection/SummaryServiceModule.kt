package app.boletinhos.summary.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.summary.SummaryService
import javax.inject.Singleton

@dagger.Module object SummaryServiceModule {
    @dagger.Provides @Singleton internal fun provideSummaryService(
        database: AppDatabase
    ): SummaryService = database.summaryService()
}