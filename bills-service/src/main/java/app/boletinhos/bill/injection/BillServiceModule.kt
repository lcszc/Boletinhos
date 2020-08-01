package app.boletinhos.bill.injection

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.BillGateway
import app.boletinhos.domain.bill.BillService
import common.AppScope
import dagger.Module
import dagger.Provides

@Module
object BillServiceModule {
    @Provides
    @AppScope
    internal fun provideBillService(
        database: AppDatabase
    ): BillService = database.billService()

    @Provides
    @AppScope
    internal fun provideBillGateway(
        database: AppDatabase
    ): BillGateway = database.billGateway()
}
