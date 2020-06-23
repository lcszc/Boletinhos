package app.boletinhos.di

import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.ManageBillService
import javax.inject.Singleton

@dagger.Module object ManageBillServiceModule {
    @dagger.Provides @Singleton internal fun provideManageBillService(
        database: AppDatabase
    ): ManageBillService = database.manageBillService()
}