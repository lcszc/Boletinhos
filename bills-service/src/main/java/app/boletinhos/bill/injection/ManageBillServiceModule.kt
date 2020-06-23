package app.boletinhos.bill.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.ManageBillService

@dagger.Module object ManageBillServiceModule {
    @dagger.Provides @common.AppScope internal fun provideManageBillService(
        database: AppDatabase
    ): ManageBillService = database.manageBillService()
}