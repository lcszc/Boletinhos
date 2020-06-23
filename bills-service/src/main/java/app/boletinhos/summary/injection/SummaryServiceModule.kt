package app.boletinhos.summary.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.summary.SummaryService

@dagger.Module
object SummaryServiceModule {
    @dagger.Provides
    @common.AppScope
    internal fun provideSummaryService(
        database: AppDatabase
    ): SummaryService = database.summaryService()
}
