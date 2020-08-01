package app.boletinhos.summary.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryService
import app.boletinhos.summary.UserSummaryPreferences
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object SummaryServiceModule {
    @Provides
    @AppScope
    internal fun provideSummaryService(
        database: AppDatabase
    ): SummaryService = database.summaryService()

    @Provides
    internal fun provideSummaryPreferences(
        userSummaryPreferences: UserSummaryPreferences
    ): SummaryPreferences = userSummaryPreferences
}
