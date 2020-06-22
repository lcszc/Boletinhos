package app.boletinhos.storage.di

import android.content.Context
import androidx.room.Room
import app.boletinhos.storage.database.AppDatabase
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
    fun billsDao(database: AppDatabase) = database.billsDao()

    @Provides
    @Singleton
    fun summaryDao(database: AppDatabase) = database.billsSummaryDao()

    @Provides
    @Singleton
    fun manageBillDao(database: AppDatabase) = database.manageBillDao()
}
