package app.boletinhos.application.injection

import android.content.Context
import androidx.room.Room
import app.boletinhos.database.AppDatabase
import common.AppContext
import common.AppScope
import common.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor

@Module
object TestDatabaseModule {
    @Provides
    @AppScope
    internal fun provideAppDatabase(
        @AppContext context: Context,
        @MainDispatcher coroutineDispatcher: CoroutineDispatcher
    ): AppDatabase {
        return Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setQueryExecutor(coroutineDispatcher.asExecutor())
            .setTransactionExecutor(coroutineDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
    }
}