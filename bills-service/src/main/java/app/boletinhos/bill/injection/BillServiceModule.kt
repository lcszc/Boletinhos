package app.boletinhos.bill.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.BillService

@dagger.Module object BillServiceModule {
    @dagger.Provides @common.AppScope internal fun provideBillService(
        database: AppDatabase
    ): BillService = database.billService()
}
