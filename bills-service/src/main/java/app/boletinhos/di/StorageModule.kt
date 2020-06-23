package app.boletinhos.di

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import app.boletinhos.domain.bill.BillService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class StorageModule {
    @Provides
    @Singleton
    fun appDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideBillService(database: AppDatabase): BillService = database.billService()

    @Provides
    @Singleton
    internal fun provideSummaryService(database: AppDatabase) = database.summaryService()

    @Provides
    @Singleton
    fun manageBillDao(database: AppDatabase) = database.manageBillDao()
}
