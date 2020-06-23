package app.boletinhos.bill.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.BillService
import javax.inject.Singleton

@dagger.Module object BillServiceModule {
    @dagger.Provides @Singleton internal fun provideBillService(
        database: AppDatabase
    ): BillService = database.billService()
}