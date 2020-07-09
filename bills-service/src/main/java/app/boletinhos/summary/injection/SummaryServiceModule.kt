package app.boletinhos.summary.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.summary.UserSummaryPreferences

@dagger.Module
object SummaryServiceModule {
    @dagger.Provides
    @common.AppScope
    internal fun provideSummaryService(
        database: AppDatabase
    ): SummaryService = database.summaryService()

    @dagger.Provides
    internal fun provideSummaryPreferences(
        userSummaryPreferences: UserSummaryPreferences
    ): SummaryPreferences = userSummaryPreferences
}
